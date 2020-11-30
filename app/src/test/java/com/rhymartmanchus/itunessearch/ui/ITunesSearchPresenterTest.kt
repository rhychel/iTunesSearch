package com.rhymartmanchus.itunessearch.ui

import com.rhymartmanchus.itunessearch.argumentCaptor
import com.rhymartmanchus.itunessearch.capture
import com.rhymartmanchus.itunessearch.coroutines.TestCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.domain.SearchSession
import com.rhymartmanchus.itunessearch.domain.Track
import com.rhymartmanchus.itunessearch.domain.exceptions.NoDataException
import com.rhymartmanchus.itunessearch.domain.interactors.GetLastSearchedTracksUseCase
import com.rhymartmanchus.itunessearch.domain.interactors.GetLastSessionUseCase
import com.rhymartmanchus.itunessearch.domain.interactors.SaveSessionUseCase
import com.rhymartmanchus.itunessearch.domain.interactors.SearchByTermUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ITunesSearchPresenterTest {

    @Mock
    private lateinit var searchByTermUseCase: SearchByTermUseCase

    @Mock
    private lateinit var saveSessionUseCase: SaveSessionUseCase

    @Mock
    private lateinit var getLastSessionUseCase: GetLastSessionUseCase

    @Mock
    private lateinit var getLastSearchedTracksUseCase: GetLastSearchedTracksUseCase

    @Mock
    private lateinit var mockView: ITunesSearchContract.View

    private val tracks = listOf(
        mock(Track::class.java),
        mock(Track::class.java),
        mock(Track::class.java)
    )

    private lateinit var iTunesSearchPresenter: ITunesSearchPresenter

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        iTunesSearchPresenter = ITunesSearchPresenter(
            TestCoroutinesDispatcher(),
            searchByTermUseCase,
            saveSessionUseCase,
            getLastSessionUseCase,
            getLastSearchedTracksUseCase
        )

        iTunesSearchPresenter.takeView(mockView)

    }

    @After
    fun tearDown() {
        iTunesSearchPresenter.detachView()
    }

    @Test
    fun `should show no visits and empty search results onViewCreated`() = runBlocking {

        `when`(getLastSessionUseCase.execute(Unit))
            .then {
                throw NoDataException("No data")
            }

        `when`(getLastSearchedTracksUseCase.execute(Unit))
            .then {
                throw NoDataException("No data")
            }

        iTunesSearchPresenter.onViewCreated()

        val order = inOrder(getLastSessionUseCase, getLastSearchedTracksUseCase, mockView, saveSessionUseCase)
        order.verify(getLastSessionUseCase).execute(Unit)
        order.verify(mockView).showNoVisitsYet()
        order.verify(getLastSearchedTracksUseCase).execute(Unit)
        order.verify(mockView).showSearchInstructions()
        order.verify(saveSessionUseCase).execute(Unit)
        order.verifyNoMoreInteractions()

    }

    @Test
    fun `should show visit datetime but with empty search results onViewCreated`() = runBlocking {

        `when`(getLastSessionUseCase.execute(Unit))
            .then {
                GetLastSessionUseCase.Response(SearchSession("Nov 29, 2020 10:39 PM"))
            }

        `when`(getLastSearchedTracksUseCase.execute(Unit))
            .then {
                throw NoDataException("No data")
            }

        iTunesSearchPresenter.onViewCreated()

        val order = inOrder(getLastSessionUseCase, getLastSearchedTracksUseCase, mockView, saveSessionUseCase)
        order.verify(getLastSessionUseCase).execute(Unit)
        order.verify(mockView).showLastVisit(com.rhymartmanchus.itunessearch.any())
        order.verify(getLastSearchedTracksUseCase).execute(Unit)
        order.verify(mockView).showSearchInstructions()
        order.verify(saveSessionUseCase).execute(Unit)
        order.verifyNoMoreInteractions()

    }

    @Test
    fun `should show visit datetime with the last search results onViewCreated`() = runBlocking {

        `when`(getLastSessionUseCase.execute(Unit))
            .then {
                GetLastSessionUseCase.Response(SearchSession("Nov 29, 2020 10:39 PM"))
            }

        `when`(getLastSearchedTracksUseCase.execute(Unit))
            .then {
                GetLastSearchedTracksUseCase.Response(tracks)
            }

        iTunesSearchPresenter.onViewCreated()

        val order = inOrder(getLastSessionUseCase, getLastSearchedTracksUseCase, mockView, saveSessionUseCase)
        order.verify(getLastSessionUseCase).execute(Unit)
        order.verify(mockView).showLastVisit(com.rhymartmanchus.itunessearch.any())
        order.verify(getLastSearchedTracksUseCase).execute(Unit)
        order.verify(mockView).hideSearchInstructions()
        order.verify(mockView).showLastSearchResults(tracks)
        order.verify(saveSessionUseCase).execute(Unit)
        order.verifyNoMoreInteractions()

    }

    @Test
    fun `should show the search results onSearchClicked with key 'life of'`() = runBlocking {

        `when`(searchByTermUseCase.execute(com.rhymartmanchus.itunessearch.any()))
            .then {
                SearchByTermUseCase.Response(tracks)
            }

        iTunesSearchPresenter.onSearchClicked("life of")

        val captor = argumentCaptor<SearchByTermUseCase.Param>()
        val order = inOrder(searchByTermUseCase, mockView, saveSessionUseCase)
        order.verify(mockView).popupLoadingDialog()
        order.verify(saveSessionUseCase).execute(Unit)
        order.verify(searchByTermUseCase).execute(capture(captor))
        order.verify(mockView).dismissLoadingDialog()
        order.verify(mockView).hideSearchInstructions()
        order.verify(mockView).showSearchResults(tracks)
        order.verifyNoMoreInteractions()

        assert(captor.value.term == "life of")
    }

    @Test
    fun `should popup no results onSearchClicked for key 'life of'`() = runBlocking {

        `when`(searchByTermUseCase.execute(com.rhymartmanchus.itunessearch.any()))
            .then {
                throw NoDataException("No data")
            }

        iTunesSearchPresenter.onSearchClicked("life of")

        val keyCaptor = argumentCaptor<String>()
        val captor = argumentCaptor<SearchByTermUseCase.Param>()
        val order = inOrder(searchByTermUseCase, mockView, saveSessionUseCase)
        order.verify(mockView).popupLoadingDialog()
        order.verify(saveSessionUseCase).execute(Unit)
        order.verify(searchByTermUseCase).execute(capture(captor))
        order.verify(mockView).dismissLoadingDialog()
        order.verify(mockView).popupNoResultsForSearchKey(capture(keyCaptor))
        order.verify(mockView).showSearchInstructions()
        order.verifyNoMoreInteractions()

        assert(captor.value.term == "life of")
        assert(keyCaptor.value == "life of")
    }

    @Test
    fun `should show a toast when key is empty`() {

        iTunesSearchPresenter.onSearchClicked("")

        verify(mockView).toastSearchKeyCannotBeEmpty()
        verifyNoMoreInteractions(mockView)

    }

    @Test
    fun `should show track details onTrackClicked`() = runBlocking {

        val track = mock(Track::class.java)

        iTunesSearchPresenter.onTrackClicked(track)

        verify(mockView).popupTrackDetails(track)
        verifyNoMoreInteractions(mockView)

    }

}
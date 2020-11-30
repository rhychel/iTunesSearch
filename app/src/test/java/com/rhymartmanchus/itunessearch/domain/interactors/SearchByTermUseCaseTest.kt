package com.rhymartmanchus.itunessearch.domain.interactors

import com.rhymartmanchus.itunessearch.any
import com.rhymartmanchus.itunessearch.argumentCaptor
import com.rhymartmanchus.itunessearch.capture
import com.rhymartmanchus.itunessearch.coroutines.TestCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.domain.SearchGateway
import com.rhymartmanchus.itunessearch.domain.Track
import com.rhymartmanchus.itunessearch.domain.exceptions.NoDataException
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SearchByTermUseCaseTest {

    @Mock
    private lateinit var searchGateway: SearchGateway

    private lateinit var searchByTermUseCase: SearchByTermUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        searchByTermUseCase = SearchByTermUseCase(
            TestCoroutinesDispatcher(),
            searchGateway
        )

    }

    @Test
    fun `should respond the 3 results as track`() = runBlocking {

        `when`(searchGateway.searchTrackByTerm(any(), any()))
            .then {
                listOf(
                    mock(Track::class.java),
                    mock(Track::class.java),
                    mock(Track::class.java)
                )
            }

        val result = searchByTermUseCase.execute(
            SearchByTermUseCase.Param("Search key", "au")
        )

        assert(result.tracks.size == 3)
    }

    @Test
    fun `should search tracks with term 'Hello'`() = runBlocking {

        `when`(searchGateway.searchTrackByTerm(any(), any()))
            .then {
                listOf(
                    mock(Track::class.java),
                    mock(Track::class.java),
                    mock(Track::class.java)
                )
            }

        searchByTermUseCase.execute(
            SearchByTermUseCase.Param("Hello", "au")
        )

        val captor = argumentCaptor<String>()
        verify(searchGateway).searchTrackByTerm(capture(captor), capture(captor))

        assert(captor.allValues[0] == "Hello")
        assert(captor.allValues[1] == "au")
    }

    @Test
    fun `should save the fetched tracks locally`() = runBlocking {

        `when`(searchGateway.searchTrackByTerm(any(), any()))
            .then {
                listOf(
                    mock(Track::class.java),
                    mock(Track::class.java),
                    mock(Track::class.java)
                )
            }

        searchByTermUseCase.execute(
            SearchByTermUseCase.Param("Hello", "au")
        ).tracks

        verify(searchGateway).searchTrackByTerm(any(), any())
        verify(searchGateway).invalidateLastSearchedTracks()
        verify(searchGateway, times(3)).saveTrack(any())
        verifyNoMoreInteractions(searchGateway)

    }

    @Test(expected = NoDataException::class)
    fun `should throw an exception when no results have found`() = runBlocking {

        `when`(searchGateway.searchTrackByTerm(any(), any()))
            .then {
                throw NoDataException("No data")
            }

        searchByTermUseCase.execute(
            SearchByTermUseCase.Param("Hello", "au")
        )

        Unit
    }

    @Test
    fun `should throw an exception when search key is blank`() = runBlocking {

        try {
            searchByTermUseCase.execute(
                SearchByTermUseCase.Param("", "")
            )

            Assert.fail()
        } catch (e: IllegalArgumentException) {
            assert(e.message!! == "term cannot be empty")
        }

    }
}
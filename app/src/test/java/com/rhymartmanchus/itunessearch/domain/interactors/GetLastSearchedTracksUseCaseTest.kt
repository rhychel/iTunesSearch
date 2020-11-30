package com.rhymartmanchus.itunessearch.domain.interactors

import com.rhymartmanchus.itunessearch.coroutines.TestCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.domain.SearchGateway
import com.rhymartmanchus.itunessearch.domain.Track
import com.rhymartmanchus.itunessearch.domain.exceptions.NoDataException
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class GetLastSearchedTracksUseCaseTest {

    @Mock
    private lateinit var searchGateway: SearchGateway

    private lateinit var getLastSearchedTracksUseCase: GetLastSearchedTracksUseCase

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        getLastSearchedTracksUseCase = GetLastSearchedTracksUseCase(
            TestCoroutinesDispatcher(),
            searchGateway
        )

    }

    @Test
    fun `should respond the last searched tracks`() = runBlocking {
        `when`(searchGateway.getLastSearchedTracks())
            .then {
                listOf(
                    mock(Track::class.java),
                    mock(Track::class.java),
                    mock(Track::class.java)
                )
            }

        val result = getLastSearchedTracksUseCase.execute(Unit).tracks

        verify(searchGateway).getLastSearchedTracks()

        assert(result.size == 3)
    }

    @Test(expected = NoDataException::class)
    fun `should throw an exception when there is no saved last searched tracks`() = runBlocking {

        `when`(searchGateway.getLastSearchedTracks())
            .then {
                throw NoDataException("No data")
            }

        getLastSearchedTracksUseCase.execute(Unit)

        Unit
    }
}
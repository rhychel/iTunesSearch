package com.rhymartmanchus.itunessearch.domain.interactors

import com.rhymartmanchus.itunessearch.coroutines.TestCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.domain.SearchGateway
import com.rhymartmanchus.itunessearch.domain.SearchSession
import com.rhymartmanchus.itunessearch.domain.exceptions.NoDataException
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class GetLastSessionUseCaseTest {

    @Mock
    private lateinit var searchGateway: SearchGateway

    private lateinit var getLastSessionUseCase: GetLastSessionUseCase

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        getLastSessionUseCase = GetLastSessionUseCase(
            TestCoroutinesDispatcher(),
            searchGateway
        )

    }

    @Test
    fun `should respond the last session when there is one`() = runBlocking {

        `when`(searchGateway.getLastSession())
            .then {
                mock(SearchSession::class.java)
            }

        getLastSessionUseCase.execute(
            Unit
        )

        verify(searchGateway).getLastSession()

        Unit
    }

    @Test(expected = NoDataException::class)
    fun `should throw an exception when no last session yet`() = runBlocking {

        `when`(searchGateway.getLastSession())
            .then {
                throw NoDataException("No last session")
            }

        getLastSessionUseCase.execute(
            Unit
        )

        Unit
    }
}
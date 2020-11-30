package com.rhymartmanchus.itunessearch.domain.interactors

import com.rhymartmanchus.itunessearch.argumentCaptor
import com.rhymartmanchus.itunessearch.capture
import com.rhymartmanchus.itunessearch.coroutines.TestCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.domain.SearchGateway
import com.rhymartmanchus.itunessearch.domain.SearchSession
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SaveSessionUseCaseTest {

    @Mock
    private lateinit var searchGateway: SearchGateway

    private lateinit var saveSessionUseCase: SaveSessionUseCase

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        saveSessionUseCase = SaveSessionUseCase(
            TestCoroutinesDispatcher(),
            searchGateway
        )

    }

    @Test
    fun `should save the search session date and time`() = runBlocking {

        saveSessionUseCase.execute(Unit)

        val captor = argumentCaptor<SearchSession>()
        verify(searchGateway).saveLastSession(capture(captor))

        assert(isValidFormat(captor.value.lastAccess))
    }

    private fun isValidFormat(dateTime: String): Boolean {
        val formatter: DateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a")
        formatter.isLenient = false
        try {
            val date: Date = formatter.parse(dateTime)
        } catch (e: ParseException) {
            return false
        }
        return true
    }
}
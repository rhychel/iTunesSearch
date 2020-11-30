package com.rhymartmanchus.itunessearch.domain.interactors

import com.rhymartmanchus.itunessearch.coroutines.AppCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.coroutines.TestCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.domain.SearchGateway
import com.rhymartmanchus.itunessearch.domain.SearchSession
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SaveSessionUseCase (
    appCoroutinesDispatcher: AppCoroutinesDispatcher,
    private val searchGateway: SearchGateway
) : UseCase<SaveSessionUseCase.Response, Unit>(appCoroutinesDispatcher) {

    class Response

    override suspend fun run(params: Unit): Response {
        val formatter: DateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a")
        formatter.isLenient = false

        val now = formatter.format(Calendar.getInstance().time)

        searchGateway.saveLastSession(
            SearchSession(now)
        )

        return Response()
    }

}
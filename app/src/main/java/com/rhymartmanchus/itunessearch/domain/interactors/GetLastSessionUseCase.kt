package com.rhymartmanchus.itunessearch.domain.interactors

import com.rhymartmanchus.itunessearch.coroutines.AppCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.domain.SearchGateway
import com.rhymartmanchus.itunessearch.domain.SearchSession

class GetLastSessionUseCase (
    dispatcher: AppCoroutinesDispatcher,
    private val searchGateway: SearchGateway
) : UseCase<GetLastSessionUseCase.Response, Unit>(dispatcher) {

    data class Response (
        val searchSession: SearchSession
    )

    override suspend fun run(params: Unit): Response {
        val result = searchGateway.getLastSession()

        return Response(
            result
        )
    }

}
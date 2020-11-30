package com.rhymartmanchus.itunessearch.domain.interactors

import com.rhymartmanchus.itunessearch.coroutines.AppCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.domain.SearchGateway
import com.rhymartmanchus.itunessearch.domain.Track
import com.rhymartmanchus.itunessearch.domain.exceptions.NoDataException

class GetLastSearchedTracksUseCase (
    dispatcher: AppCoroutinesDispatcher,
    private val searchGateway: SearchGateway
) : UseCase<GetLastSearchedTracksUseCase.Response, Unit>(dispatcher) {

    data class Response (
        val tracks: List<Track>
    )

    override suspend fun run(params: Unit): Response {
        val result = searchGateway.getLastSearchedTracks()

        return Response(
            result
        )
    }

}
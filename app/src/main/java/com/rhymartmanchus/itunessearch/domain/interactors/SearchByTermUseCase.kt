package com.rhymartmanchus.itunessearch.domain.interactors

import com.rhymartmanchus.itunessearch.coroutines.AppCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.domain.SearchGateway
import com.rhymartmanchus.itunessearch.domain.Track
import com.rhymartmanchus.itunessearch.domain.exceptions.NoDataException

class SearchByTermUseCase (
    dispatcher: AppCoroutinesDispatcher,
    private val searchGateway: SearchGateway
) : UseCase<SearchByTermUseCase.Response, SearchByTermUseCase.Param>(dispatcher) {


    data class Param (
        val term: String,
        val countryCode: String
    )

    data class Response (
        val tracks: List<Track>
    )

    override suspend fun run(params: Param): Response {
        val tracks = searchGateway.searchTrackByTerm(params.term, params.countryCode)

        if(params.term.isEmpty())
            throw IllegalArgumentException("term cannot be empty")

        searchGateway.invalidateLastSearchedTracks()

        tracks.forEach {
            searchGateway.saveTrack(it)
        }

        return Response(
            tracks
        )
    }

}
package com.rhymartmanchus.itunessearch.data

import com.rhymartmanchus.itunessearch.data.api.ITunesEndpoints
import com.rhymartmanchus.itunessearch.domain.Track
import com.rhymartmanchus.itunessearch.domain.exceptions.NoDataException
import retrofit2.Retrofit

class SearchRemoteServiceProvider (
    private val webservice: Retrofit
) : SearchRemoteService {

    private val endpoints by lazy {
        webservice.create(ITunesEndpoints::class.java)
    }

    override suspend fun searchByTerm(term: String, countryCode: String): List<Track> {
        val tracks = endpoints.search(
            term,
            countryCode,
            "movie"
        )

        if(tracks.isEmpty())
            throw NoDataException("No data")

        return tracks.map {
            it.toDomain()
        }
    }
}
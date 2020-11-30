package com.rhymartmanchus.itunessearch.data

import com.rhymartmanchus.itunessearch.domain.Track

interface SearchRemoteService {

    suspend fun searchByTerm(term: String, countryCode: String): List<Track>

}
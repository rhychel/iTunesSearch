package com.rhymartmanchus.itunessearch.data.api

import com.rhymartmanchus.itunessearch.data.models.TrackRaw
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ITunesEndpoints {

    @GET("/search")
    suspend fun search(
        @Query("term") term: String,
        @Query("country") country: String,
        @Query("media") media: String
    ): List<TrackRaw>

}
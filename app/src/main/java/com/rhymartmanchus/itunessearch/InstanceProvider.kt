package com.rhymartmanchus.itunessearch

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rhymartmanchus.itunessearch.coroutines.AppCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.coroutines.CoroutinesDispatcher
import com.rhymartmanchus.itunessearch.data.SearchGatewayImpl
import com.rhymartmanchus.itunessearch.data.SearchLocalServiceProvider
import com.rhymartmanchus.itunessearch.data.SearchRemoteServiceProvider
import com.rhymartmanchus.itunessearch.data.api.TrackRawConverter
import com.rhymartmanchus.itunessearch.data.db.ITunesSearchDatabase
import com.rhymartmanchus.itunessearch.data.models.TrackRaw
import com.rhymartmanchus.itunessearch.domain.SearchGateway
import com.rhymartmanchus.itunessearch.domain.interactors.GetLastSearchedTracksUseCase
import com.rhymartmanchus.itunessearch.domain.interactors.GetLastSessionUseCase
import com.rhymartmanchus.itunessearch.domain.interactors.SaveSessionUseCase
import com.rhymartmanchus.itunessearch.domain.interactors.SearchByTermUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InstanceProvider {

    var dispatcher: AppCoroutinesDispatcher = CoroutinesDispatcher()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(object : TypeToken<MutableList<TrackRaw>>(){}.type, TrackRawConverter())
                        .create()
                )
            )
            .build()
    }

    private lateinit var searchGateway: SearchGateway

    fun initialize(context: Context) {
        searchGateway = SearchGatewayImpl(
            SearchLocalServiceProvider(
                ITunesSearchDatabase.getInstance(context).iTunesSearchDao()
            ),
            SearchRemoteServiceProvider(
                retrofit
            )
        )
    }

    fun provideGetLastSearchedTracksUseCase(): GetLastSearchedTracksUseCase =
        GetLastSearchedTracksUseCase(
            dispatcher,
            searchGateway
        )

    fun provideGetLastSessionUseCase(): GetLastSessionUseCase =
        GetLastSessionUseCase(
            dispatcher,
            searchGateway
        )

    fun provideSaveSessionUseCase(): SaveSessionUseCase =
        SaveSessionUseCase(
            dispatcher,
            searchGateway
        )

    fun provideSearchByTermUseCase(): SearchByTermUseCase =
        SearchByTermUseCase(
            dispatcher,
            searchGateway
        )

}
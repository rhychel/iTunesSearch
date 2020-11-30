package com.rhymartmanchus.itunessearch.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rhymartmanchus.itunessearch.data.models.SearchSessionsDB
import com.rhymartmanchus.itunessearch.data.models.TracksDB

@Dao
interface ITunesSearchDAO {

    @Query("SELECT * FROM search_sessions")
    suspend fun getSearchSession(): SearchSessionsDB?

    @Query("SELECT * FROM tracks")
    suspend fun getLastSearchedTracks(): List<TracksDB>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSearchSession(searchSessionsDB: SearchSessionsDB)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrack(tracksDB: TracksDB)

    @Query ("DELETE FROM tracks")
    suspend fun deleteTracks()

}
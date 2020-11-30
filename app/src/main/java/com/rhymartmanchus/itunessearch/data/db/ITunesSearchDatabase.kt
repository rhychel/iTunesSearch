package com.rhymartmanchus.itunessearch.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rhymartmanchus.itunessearch.data.models.SearchSessionsDB
import com.rhymartmanchus.itunessearch.data.models.TracksDB

@Database (
    entities = [
        SearchSessionsDB::class,
        TracksDB::class
    ],
    version = 1
)
abstract class ITunesSearchDatabase : RoomDatabase() {

    abstract fun iTunesSearchDao(): ITunesSearchDAO

    companion object {

        internal var INSTANCE: ITunesSearchDatabase? = null

        fun getInstance(appContext: Context): ITunesSearchDatabase {
            if(INSTANCE == null) {
                synchronized(ITunesSearchDatabase::class) {
                    INSTANCE = Room.databaseBuilder(appContext, ITunesSearchDatabase::class.java, "itunes-search-database.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}
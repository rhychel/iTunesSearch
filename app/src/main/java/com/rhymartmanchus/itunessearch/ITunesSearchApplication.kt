package com.rhymartmanchus.itunessearch

import android.app.Application

class ITunesSearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        InstanceProvider.initialize(this)
    }

}
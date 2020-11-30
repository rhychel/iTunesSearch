package com.rhymartmanchus.itunessearch.coroutines

import kotlin.coroutines.CoroutineContext

interface AppCoroutinesDispatcher {

    fun io(): CoroutineContext
    fun ui(): CoroutineContext

}
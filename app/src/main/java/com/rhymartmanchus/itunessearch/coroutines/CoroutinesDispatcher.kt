package com.rhymartmanchus.itunessearch.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class CoroutinesDispatcher : AppCoroutinesDispatcher {
    override fun io(): CoroutineContext = Dispatchers.IO
    override fun ui(): CoroutineContext = Dispatchers.Main
}
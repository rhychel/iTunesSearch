package com.rhymartmanchus.itunessearch.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoroutinesDispatcher : AppCoroutinesDispatcher {
    override fun io(): CoroutineContext = Dispatchers.Unconfined
    override fun ui(): CoroutineContext = Dispatchers.Unconfined
}
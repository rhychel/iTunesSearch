package com.rhymartmanchus.itunessearch.domain.interactors

import com.rhymartmanchus.itunessearch.coroutines.AppCoroutinesDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<Response, Params> (
    private val dispatcher: AppCoroutinesDispatcher
) {

    protected abstract suspend fun run(params: Params): Response

    suspend fun execute(params: Params): Response = withContext(dispatcher.io()) {
        run(params)
    }

}
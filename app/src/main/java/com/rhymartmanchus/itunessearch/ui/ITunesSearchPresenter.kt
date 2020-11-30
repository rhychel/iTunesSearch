package com.rhymartmanchus.itunessearch.ui

import com.rhymartmanchus.itunessearch.coroutines.AppCoroutinesDispatcher
import com.rhymartmanchus.itunessearch.domain.Track
import com.rhymartmanchus.itunessearch.domain.exceptions.NoDataException
import com.rhymartmanchus.itunessearch.domain.interactors.GetLastSearchedTracksUseCase
import com.rhymartmanchus.itunessearch.domain.interactors.GetLastSessionUseCase
import com.rhymartmanchus.itunessearch.domain.interactors.SaveSessionUseCase
import com.rhymartmanchus.itunessearch.domain.interactors.SearchByTermUseCase
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class ITunesSearchPresenter (
    private val dispatcher: AppCoroutinesDispatcher,
    private val searchByTermUseCase: SearchByTermUseCase,
    private val saveSessionUseCase: SaveSessionUseCase,
    private val getLastSessionUseCase: GetLastSessionUseCase,
    private val getLastSearchedTracksUseCase: GetLastSearchedTracksUseCase
) : ITunesSearchContract.Presenter, CoroutineScope {

    private var view: ITunesSearchContract.View? = null
    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = dispatcher.io() + job

    override fun takeView(view: ITunesSearchContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onViewCreated() {
        launch {

            try {
                val searchSession = getLastSessionUseCase.execute(Unit).searchSession
                withContext(dispatcher.ui()) {
                    view?.showLastVisit(searchSession.lastAccess)
                }
            } catch (e: NoDataException) {
                withContext(dispatcher.ui()) {
                    view?.showNoVisitsYet()
                }
            }

            try {
                val tracks = getLastSearchedTracksUseCase.execute(Unit).tracks
                withContext(dispatcher.ui()) {
                    view?.hideSearchInstructions()
                    view?.showLastSearchResults(tracks)
                }
            } catch (e: NoDataException) {
                withContext(dispatcher.ui()) {
                    view?.showSearchInstructions()
                }
            }

            saveSessionUseCase.execute(Unit)
        }
    }

    override fun onSearchClicked(searchKey: String) {

        if(searchKey.isEmpty()) {
            view?.toastSearchKeyCannotBeEmpty()
            return
        }

        view?.popupLoadingDialog()
        launch {
            saveSessionUseCase.execute(Unit)
            try {
                val tracks = searchByTermUseCase.execute(
                    SearchByTermUseCase.Param(searchKey, Locale.getDefault().country)
                ).tracks
                withContext(dispatcher.ui()) {
                    view?.dismissLoadingDialog()
                    view?.hideSearchInstructions()
                    view?.showSearchResults(tracks)
                }
            } catch (e: NoDataException) {
                withContext(dispatcher.ui()) {
                    view?.dismissLoadingDialog()
                    view?.popupNoResultsForSearchKey(searchKey)
                    view?.showSearchInstructions()
                }
            }
        }
    }

    override fun onTrackClicked(track: Track) {
        view?.popupTrackDetails(track)
    }

    override fun onViewDestroyed() {
        job.cancel()
    }

}
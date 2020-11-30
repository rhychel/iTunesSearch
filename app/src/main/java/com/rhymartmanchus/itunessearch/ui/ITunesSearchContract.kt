package com.rhymartmanchus.itunessearch.ui

import com.rhymartmanchus.itunessearch.domain.Track

interface ITunesSearchContract {

    interface View {

        fun popupLoadingDialog()
        fun dismissLoadingDialog()

        fun popupNoResultsForSearchKey(searchKey: String)
        fun showSearchResults(tracks: List<Track>)
        fun showLastSearchResults(tracks: List<Track>)
        fun showSearchInstructions()
        fun showLastVisit(lastVisit: String)
        fun showNoVisitsYet()
        fun popupTrackDetails(track: Track)
        fun toastSearchKeyCannotBeEmpty()
        fun hideSearchInstructions()

    }

    interface Presenter {

        fun takeView(view: View)
        fun detachView()

        fun onViewCreated()
        fun onSearchClicked(searchKey: String)
        fun onTrackClicked(track: Track)
        fun onViewDestroyed()

    }

}
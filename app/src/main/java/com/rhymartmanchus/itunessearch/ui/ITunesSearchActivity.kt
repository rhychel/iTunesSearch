package com.rhymartmanchus.itunessearch.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhymartmanchus.itunessearch.InstanceProvider
import com.rhymartmanchus.itunessearch.R
import com.rhymartmanchus.itunessearch.adapters.TrackItem
import com.rhymartmanchus.itunessearch.databinding.ActivityMainBinding
import com.rhymartmanchus.itunessearch.domain.Track
import eu.davidea.flexibleadapter.FlexibleAdapter

class ITunesSearchActivity : AppCompatActivity(), ITunesSearchContract.View {

    private val presenter: ITunesSearchContract.Presenter by lazy {
        ITunesSearchPresenter(
            InstanceProvider.dispatcher,
            InstanceProvider.provideSearchByTermUseCase(),
            InstanceProvider.provideSaveSessionUseCase(),
            InstanceProvider.provideGetLastSessionUseCase(),
            InstanceProvider.provideGetLastSearchedTracksUseCase()
        )
    }

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    private val binder: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter: FlexibleAdapter<TrackItem> by lazy {
        FlexibleAdapter<TrackItem>(mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)

        bindAdapter()
        bindListeners()

        presenter.takeView(this)
        presenter.onViewCreated()
    }

    private fun bindAdapter() {

        adapter.mItemClickListener = FlexibleAdapter.OnItemClickListener { _, position ->

            val trackItem = adapter.getItem(position)!!
            presenter.onTrackClicked(trackItem.track)

            true
        }

        binder.rvResults.setHasFixedSize(true)
        binder.rvResults.layoutManager = LinearLayoutManager(this)
        binder.rvResults.adapter = adapter
    }

    private fun bindListeners() {
        binder.btnSearch.setOnClickListener {
            presenter.onSearchClicked(
                binder.etSearchKey.text.toString()
            )
        }

        binder.etSearchKey.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.onSearchClicked(
                    binder.etSearchKey.text.toString()
                )
                true
            }
            else
                false
        }
    }

    override fun popupLoadingDialog() {
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Searching...")
        progressDialog.show()
    }

    override fun dismissLoadingDialog() {
        progressDialog.dismiss()
    }

    override fun popupNoResultsForSearchKey(searchKey: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Ooops!")
        dialog.setMessage("No search results for \"$searchKey\"")
        dialog.setPositiveButton("Ok") { _,_-> }
        dialog.show()
    }

    override fun showSearchResults(tracks: List<Track>) {

        adapter.clear()
        adapter.updateDataSet(tracks.map {
            TrackItem(it)
        })
        adapter.notifyDataSetChanged()

    }

    override fun showLastSearchResults(tracks: List<Track>) {

        adapter.clear()
        adapter.updateDataSet(tracks.map {
            TrackItem(it)
        })
        adapter.notifyDataSetChanged()

    }

    override fun showSearchInstructions() {
        binder.tvSearchInstructions.visibility = View.VISIBLE
    }

    override fun hideSearchInstructions() {
        binder.tvSearchInstructions.visibility = View.GONE
    }

    override fun showLastVisit(lastVisit: String) {
        binder.tvLastVisit.text = "Last visit: $lastVisit"
    }

    override fun showNoVisitsYet() {
        binder.tvLastVisit.text = "No visits yet"
    }

    override fun popupTrackDetails(track: Track) {
        val dialog = TrackDetailsDialogFragment.getInstance(track)
        dialog.show(supportFragmentManager, "track_details_dialog")
    }

    override fun toastSearchKeyCannotBeEmpty() {
        Toast.makeText(this, "Search key cannot be empty", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        presenter.detachView()
        presenter.onViewDestroyed()
        super.onDestroy()
    }
}
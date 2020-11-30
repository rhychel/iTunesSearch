package com.rhymartmanchus.itunessearch.ui

import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDialogFragment
import com.rhymartmanchus.itunessearch.R
import com.rhymartmanchus.itunessearch.databinding.DialogTrackDetails2Binding
import com.rhymartmanchus.itunessearch.domain.Track
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class TrackDetailsDialogFragment : AppCompatDialogFragment() {

    lateinit var track: Track

    companion object {
        fun getInstance(track: Track): TrackDetailsDialogFragment {
            val dialog = TrackDetailsDialogFragment()
            dialog.track = track
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_track_details2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binder = DialogTrackDetails2Binding.bind(view)
        binder.tvTrackName.text = track.name
        binder.tvGenre.text = track.genre
        binder.tvPrice.text = String.format("%s %.2f",
            track.currency, track.price)
        binder.tvLongDescription.text = track.longDescription
        binder.ivArtwork.post {

            val height = 400.takeIf { binder.ivArtwork.height == 0 } ?: binder.ivArtwork.height
            val width = 200.takeIf { binder.ivArtwork.width == 0 } ?: binder.ivArtwork.width

            Picasso.get()
                .load(track.artworkUrl)
                .config(Bitmap.Config.ARGB_8888)
                .fit()
                .placeholder(R.drawable.ic_placeholder)
                .into(binder.ivArtwork, object : Callback {
                    override fun onSuccess() {
                        binder.pbLoader.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        binder.pbLoader.visibility = View.GONE
                    }
                })
        }
        binder.ibtnClose.setOnClickListener {
            dismiss()
        }

    }


    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout((size.x * 0.95).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }
}
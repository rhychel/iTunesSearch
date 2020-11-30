package com.rhymartmanchus.itunessearch.adapters

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rhymartmanchus.itunessearch.R
import com.rhymartmanchus.itunessearch.domain.Track
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import java.lang.Exception
import java.util.*

data class TrackItem (
    val track: Track
) : AbstractFlexibleItem<TrackItem.TrackItemVH>() {

    inner class TrackItemVH (
        view: View,
        adapter: FlexibleAdapter<*>?
    ) : FlexibleViewHolder(view, adapter) {

        val ivArtwork: ImageView by lazy {
            view.findViewById<ImageView>(R.id.ivArtwork)
        }
        val tvTrackName: TextView by lazy {
            view.findViewById<TextView>(R.id.tvTrackName)
        }
        val tvPrice: TextView by lazy {
            view.findViewById<TextView>(R.id.tvPrice)
        }
        val tvGenre: TextView by lazy {
            view.findViewById<TextView>(R.id.tvGenre)
        }
        val pbLoader: ProgressBar by lazy {
            view.findViewById<ProgressBar>(R.id.pbLoader)
        }

    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: TrackItemVH,
        position: Int,
        payloads: MutableList<Any>?
    ) {

        holder.tvGenre.text = track.genre
        holder.tvPrice.text = String.format("%s %.2f",
            track.currency, track.price)
        holder.tvTrackName.text = track.name

        holder.pbLoader.visibility = View.VISIBLE
        holder.ivArtwork.post {

            val height = 200.takeIf { holder.ivArtwork.height == 0 } ?: holder.ivArtwork.height
            val width = 200.takeIf { holder.ivArtwork.width == 0 } ?: holder.ivArtwork.width

            Picasso.get()
                .load(track.artworkUrl)
                .config(Bitmap.Config.ARGB_8888)
                .resize(width, height)
                .centerInside()
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.ivArtwork, object : Callback {
                    override fun onSuccess() {
                        holder.pbLoader.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        holder.pbLoader.visibility = View.GONE
                    }
                })
        }

    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ): TrackItemVH = TrackItemVH(view, adapter)

    override fun getLayoutRes(): Int = R.layout.layout_result_item

}
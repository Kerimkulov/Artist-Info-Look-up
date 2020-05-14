package com.example.artistinfolookup.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artistinfolookup.R
import com.example.artistinfolookup.networking.Track
import kotlinx.android.synthetic.main.album_item_layout.view.*
import kotlinx.android.synthetic.main.track_item_layout.view.*

class TrackAdapter(
    private val trackList: List<Track> = listOf()
) :RecyclerView.Adapter<TrackAdapter.HintViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.track_item_layout, parent, false)
        return HintViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trackList.count()
    }

    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        holder.bindHint(trackList[position])
    }


    inner class HintViewHolder(
        private val view: View
    ):RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bindHint(track: Track){


            if (track.strTrack != null){
                view.track_name.text = track.strTrack
            }
            else{
                view.album_title.text = ""
            }
            if (track.intDuration != null){
                val minute = (track.intDuration / (1000 * 60))
                val second = (track.intDuration / 1000) % 60
                view.track_dur.text = "${minute}:${second}"
            }
            else{
                view.track_dur.text = ""
            }
        }
    }
}
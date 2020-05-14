package com.example.artistinfolookup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artistinfolookup.R
import com.example.artistinfolookup.networking.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.artist_video_layout.view.*

class VideoAdapter(
    private val videoList: List<Video> = listOf(),
    private val onVideoClick:(Video) -> Unit
) :RecyclerView.Adapter<VideoAdapter.HintViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.artist_video_layout, parent, false)
        return HintViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoList.count()
    }

    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        holder.bindHint(videoList[position])
    }


    inner class HintViewHolder(
        private val view: View
    ):RecyclerView.ViewHolder(view){
        fun bindHint(video: Video){
            if(video.strTrackThumb == null || video.strTrackThumb.isEmpty()){
                view.artist_video_thumb.setImageResource(R.drawable.notfound)
            }
            else {
                Picasso
                    .get()
                    .load(video.strTrackThumb)
                    .into(view.artist_video_thumb)
            }
            view.video_name.text = video.strTrack
            view.setOnClickListener{
                onVideoClick(video)
            }
        }
    }
}
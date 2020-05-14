package com.example.artistinfolookup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artistinfolookup.R
import com.example.artistinfolookup.networking.Artist
import com.example.artistinfolookup.networking.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.artist_item_layout.view.*
import kotlinx.android.synthetic.main.artist_video_layout.view.*

class FavouriteAdapter(
    private val artistList: List<Artist> = listOf(),
    private val onArtistClick:(Artist) -> Unit
) :RecyclerView.Adapter<FavouriteAdapter.HintViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.artist_item_layout, parent, false)
        return HintViewHolder(view)
    }

    override fun getItemCount(): Int {
        return artistList.count()
    }

    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        holder.bindHint(artistList[position])
    }


    inner class HintViewHolder(
        private val view: View
    ):RecyclerView.ViewHolder(view){
        fun bindHint(artist: Artist){
            if(artist.strArtistThumb == null || artist.strArtistThumb.isEmpty()){
                view.artist_fav_thumb.setImageResource(R.drawable.notfound)
            }
            else {
                Picasso
                    .get()
                    .load(artist.strArtistThumb)
                    .into(view.artist_fav_thumb)
            }
            view.fav_artist_name.text = artist.strArtist
            view.setOnClickListener{
                onArtistClick(artist)
            }
        }
    }
}
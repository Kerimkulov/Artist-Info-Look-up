package com.example.artistinfolookup.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.artistinfolookup.R
import com.example.artistinfolookup.networking.Album
import kotlinx.android.synthetic.main.album_item_layout.view.*

class MyAdapter(
    private val albumList: List<Album> = listOf(),
    private val onAlbumClick:(Album) -> Unit
) :RecyclerView.Adapter<MyAdapter.HintViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.album_item_layout, parent, false)
        return HintViewHolder(view)
    }

    override fun getItemCount(): Int {
        return albumList.count()
    }

    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        holder.bindHint(albumList[position])
    }


    inner class HintViewHolder(
        private val view: View
    ):RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bindHint(album: Album){
            if (album.strAlbum != null){
                view.album_title.text = album.strAlbum
            }
            else{
                view.album_title.text = ""
            }
            if (album.intYearReleased != null || album.intYearReleased == 0){
                view.album_year.text = album.intYearReleased.toString()
            }
            else{
                view.album_year.text = ""
            }

            if (album.strGenre != null){
                view.album_genre.text = "Genres: ${album.strGenre}"
            }
            else{
                view.album_genre.text = ""
            }

            view.setOnClickListener{
                onAlbumClick(album)
            }
        }
    }
}
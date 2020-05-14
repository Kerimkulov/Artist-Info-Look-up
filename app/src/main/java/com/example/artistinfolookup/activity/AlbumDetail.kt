package com.example.artistinfolookup.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artistinfolookup.Constants
import com.example.artistinfolookup.R
import com.example.artistinfolookup.adapter.TrackAdapter
import com.example.artistinfolookup.networking.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album_detail.*

class AlbumDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        track_list.layoutManager = LinearLayoutManager(this)
        val albumName = intent.getStringExtra(Constants.ALBUM_NAME)!!
        val albumId = intent.getStringExtra(Constants.ALBUM_ID)!!
        Log.d("taaag", albumName)
        loadTracks(albumId)
        AlbumDetailLoader(
            onSuccess = { response ->
                    for (album in response.album!!) {
                            albumDetails(album)
                    }
                Log.d("detail", response.toString())
            },
            onError = {
                Log.d("taaag", it.message!!)
            },
            key = albumName
        ).loadAlbumDetail()
    }

    private fun loadTracks(id: String){
        TrackLoader(
            onSuccess = { response ->
                displayTracks(response, id)
            },
            onError = {
                Log.d("taaag", it.message!!)
            },
            key = id
        ).loadTracks()
    }

    @SuppressLint("SetTextI18n")
    private fun albumDetails(album: Album){

        if(album.strAlbumThumb == null || album.strAlbumThumb.isEmpty()){
            album_detail_thumb.setImageResource(R.drawable.notfound)
        }
        else {

            Picasso
                .get()
                .load(album.strAlbumThumb)
                .into(album_detail_thumb)
        }
        if (album.strDescriptionEN == null){
            album_desc.text = ""
        }else{
            album_desc.text = album.strDescriptionEN
        }
        album_name.text = "Album name: ${album.strAlbum}"

    }

    private fun displayTracks(response: TrackList, id:String){
        track_list.adapter = TrackAdapter(
            trackList = response.track
        )
    }

}

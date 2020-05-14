
package com.example.artistinfolookup.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artistinfolookup.Constants
import com.example.artistinfolookup.R
import com.example.artistinfolookup.adapter.MyAdapter
import com.example.artistinfolookup.networking.ArtistAlbums
import com.example.artistinfolookup.networking.ArtistAlbumsLoader
import kotlinx.android.synthetic.main.activity_artist_album.*

class ArtistAlbum : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_album)

        album_list.layoutManager = LinearLayoutManager(this)
        val artistName = intent.getStringExtra(Constants.ARTIST_NAME)!!
        Log.d("taaag", artistName)
        ArtistAlbumsLoader(
            onSuccess = { response ->
                print(response)
                displayAlbums(response)
            },
            onError = {
                Log.d("taaag", it.message!!)
            },
            key = artistName
        ).loadArtistAlbums()
    }
    private fun displayAlbums(response: ArtistAlbums){
        album_list.adapter = MyAdapter(
            albumList = response.album, onAlbumClick = { album ->
                val intent = Intent(this, AlbumDetail::class.java)
                intent.putExtra(Constants.ALBUM_NAME, album.strAlbum)
                intent.putExtra(Constants.ALBUM_ID, album.idAlbum)
                startActivity(intent)
            })
    }

}

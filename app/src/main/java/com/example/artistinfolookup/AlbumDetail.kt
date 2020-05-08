package com.example.artistinfolookup

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.artistinfolookup.networking.Album
import com.example.artistinfolookup.networking.AlbumDetailLoader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album_detail.*
import retrofit2.Response

class AlbumDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        val albumName = intent.getStringExtra(ArtistAlbum.ALBUM_NAME)!!
        Log.d("taaag", albumName)
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

}

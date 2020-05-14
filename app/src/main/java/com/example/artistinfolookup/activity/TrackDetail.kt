package com.example.artistinfolookup.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artistinfolookup.Constants
import com.example.artistinfolookup.R
import com.example.artistinfolookup.adapter.VideoAdapter
import com.example.artistinfolookup.networking.ArtistVideo
import com.example.artistinfolookup.networking.VIdeoLoader
import kotlinx.android.synthetic.main.activity_track_detail.*

class TrackDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_detail)
        artist_video_list.layoutManager = LinearLayoutManager(this)
        val id = intent.getStringExtra(Constants.ARTIST_ID)!!
        VIdeoLoader(
                onSuccess = { response ->
                    displayArtistVideoDetail(response)
                },
                onError = {
                    Log.d("taaag", it.message!!)
                },
                key = id
            ).loadVideo()
    }

    private fun displayArtistVideoDetail(response: ArtistVideo){
        artist_video_list.adapter =
            response.mvids?.let {
                VideoAdapter(
                    videoList = it, onVideoClick = { video ->
                        if (video.strMusicVid == null || video.strMusicVid == "") {
                            Toast.makeText(this, "video uri is empty", Toast.LENGTH_LONG).show()
                        } else {
                            val intent =
                                Intent(Intent.ACTION_VIEW, Uri.parse(video.strMusicVid))
                            startActivity(intent)
                        }
                    })
            }
    }
}

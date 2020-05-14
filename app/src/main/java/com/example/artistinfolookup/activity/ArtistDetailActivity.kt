package com.example.artistinfolookup.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import com.example.artistinfolookup.Constants
import com.example.artistinfolookup.R
import com.example.artistinfolookup.networking.Artist
import com.example.artistinfolookup.networking.DataLoader
import com.example.artistinfolookup.networking.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_artist_detail.*

class ArtistDetailActivity : AppCompatActivity() {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_detail)

        val artist = intent.getParcelableExtra<Artist>(Constants.ARTIST)!!
        if (auth.currentUser != null) {
            loadArtist(artist, auth.currentUser!!.uid)
        }
    }

    private fun loadArtist(artist: Artist, uid: String){
        db.collection(Constants.USER_COLLECTION)
            .document(uid)
            .addSnapshotListener{querySnapshot, e ->
                if (e != null)
                {
                    Log.d("eeeerrrr", e.message)
                    return@addSnapshotListener
                }

                val user = querySnapshot?.toObject(User::class.java)
                if (user != null){
                    if (user.artists.contains(artist)){
                        displayArtistInfo(artist)
                    }
                }
            }
    }

    private fun displayArtistInfo(artist: Artist){


        if(artist.strArtistThumb == null || artist.strArtistThumb!!.isEmpty()){
            artist_detail_thumb.setImageResource(R.drawable.notfound)
        }
        else {
            Picasso
                .get()
                .load(artist.strArtistThumb)
                .into(artist_detail_thumb)
        }
        if(artist.strArtistLogo == null || artist.strArtistLogo!!.isEmpty()){
            artist_detail_logo.setImageResource(R.drawable.notfound)
        }
        else {
            Picasso
                .get()
                .load(artist.strArtistLogo)
                .into(artist_detail_logo)
        }
        artist_detail_biog.text = artist.strBiographyEN
        artist_detail_name.text = artist.strArtist
        artist_detail_year.text = artist.intBornYear.toString()
        if (artist.strWebsite != null) {
            artist_detail_website.text = artist.strWebsite
        }
        else{
            artist_detail_website.text = R.string.no_website.toString()
        }
        user_detail_albums.setOnClickListener {
            val intent = Intent(this, ArtistAlbum::class.java)
            intent.putExtra(Constants.ARTIST_NAME, artist.strArtist)
            startActivity(intent)
        }
        artist_detail_videos.setOnClickListener {
            val intent = Intent(this, TrackDetail::class.java)
            intent.putExtra(Constants.ARTIST_ID, artist.idArtist)
            startActivity(intent)
        }

    }
}

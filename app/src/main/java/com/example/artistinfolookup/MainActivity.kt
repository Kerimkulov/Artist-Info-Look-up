package com.example.artistinfolookup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.example.artistinfolookup.networking.Artist
import com.example.artistinfolookup.networking.ArtistInfo
import com.example.artistinfolookup.networking.DataLoader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        find()
    }

    private fun find(){
        val search = findViewById<SearchView>(R.id.search_view)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                loadArtist(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                loadArtist(newText)
                return false
            }
        })
    }

    private fun loadArtist(artistN: String){
        DataLoader(
            onSuccess = { response ->
                if (response.artists != null) {
                    searchByArtistName(response.artists)
                }

            },
            onError = {
                Log.d("taaag", it.message)
            },
            key = artistN
        ).loadData()
    }

    private fun searchByArtistName(artists: List<Artist>){
        for (artist in artists){

            if(artist.strArtistThumb == null || artist.strArtistThumb.isEmpty()){
                artist_thumb.setImageResource(R.drawable.notfound)
            }
            else {
                Picasso
                    .get()
                    .load(artist.strArtistThumb)
                    .into(artist_thumb)
            }
            if(artist.strArtistLogo == null || artist.strArtistLogo.isEmpty()){
                artist_logo.setImageResource(R.drawable.notfound)
            }
            else {
                Picasso
                    .get()
                    .load(artist.strArtistLogo)
                    .into(artist_logo)
            }



            artist_biog.text = artist.strBiographyEN
            artist_name.text = artist.strArtist
            artist_year.text = artist.intBornYear.toString()
            if (artist.strWebsite != null) {
                artist_website.text = artist.strWebsite
            }
            else{
                artist_website.text = R.string.no_website.toString()
            }
            artist_thumb.setOnClickListener {
                val intent = Intent(this, ArtistAlbum::class.java)
                intent.putExtra(ArtistAlbum.ARTIST_NAME, artist.strArtist)
                startActivity(intent)
            }
        }
    }

}

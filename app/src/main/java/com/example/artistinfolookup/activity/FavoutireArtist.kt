package com.example.artistinfolookup.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artistinfolookup.Constants
import com.example.artistinfolookup.R
import com.example.artistinfolookup.adapter.FavouriteAdapter
import com.example.artistinfolookup.networking.Artist
import com.example.artistinfolookup.networking.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_favoutire_artist.*
import kotlin.math.log

class FavoutireArtist : AppCompatActivity() {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoutire_artist)

        fav_list.layoutManager = LinearLayoutManager(this)
        if (auth.currentUser != null){
            loadFav(auth.currentUser!!.uid)
        }
    }
    private fun loadFav(uid: String){
        db.collection(Constants.USER_COLLECTION)
            .document(uid)
            .addSnapshotListener{snapshot, e->
                if (e != null){
                    Log.d("errrorr", e.localizedMessage)
                    return@addSnapshotListener
                }

                val user = snapshot?.toObject(User::class.java)
                if (user != null){
                    displayFavArtists(user.artists)
                }
                else{
                    displayFavArtists(emptyList())
                }
            }
    }
    private fun displayFavArtists(artists: List<Artist>){
        fav_list.adapter = FavouriteAdapter(
            artistList = artists, onArtistClick = { artist ->
                val intent = Intent(this, ArtistDetailActivity::class.java)
                intent.putExtra(Constants.ARTIST, artist)
                startActivity(intent)
            })
    }
}

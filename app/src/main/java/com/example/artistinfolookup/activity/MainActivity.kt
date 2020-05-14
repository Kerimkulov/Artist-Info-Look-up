package com.example.artistinfolookup.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.artistinfolookup.Constants
import com.example.artistinfolookup.R
import com.example.artistinfolookup.networking.Artist
import com.example.artistinfolookup.networking.DataLoader
import com.example.artistinfolookup.networking.User
import com.example.simplechatapp.observer.ForegroundBackgroundListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_fav.isEnabled = false
        find()
        setSupportActionBar(artist_info)
        ProcessLifecycleOwner.get()
            .lifecycle.addObserver(
                ForegroundBackgroundListener()
                    .also { this }
            )
    }



    private fun find(){
        val search = findViewById<SearchView>(R.id.search_view)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                loadArtist(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
//                add_fav.isChecked = false
                loadArtist(newText)
                return false
            }
        })
    }

    private fun isFav(artist: Artist, uid: String){
        db.collection(Constants.USER_COLLECTION)
            .document(uid)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null){
                    Log.d("eeee", e.localizedMessage)
                    return@addSnapshotListener
                }

                val user = querySnapshot?.toObject(User::class.java)
                if (user != null){
                    add_fav.isChecked = user.artists.contains(artist)
                }

            }
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
            if (auth.currentUser != null) {
               isFav(artists[0], auth.currentUser!!.uid)
                addFav(artists[0])
            }
            if(artists[0].strArtistThumb == null || artists[0].strArtistThumb!!.isEmpty()){
                artist_thumb.setImageResource(R.drawable.notfound)
            }
            else {
                Picasso
                    .get()
                    .load(artists[0].strArtistThumb)
                    .into(artist_thumb)
            }
            if(artists[0].strArtistLogo == null || artists[0].strArtistLogo!!.isEmpty()){
                artist_logo.setImageResource(R.drawable.notfound)
            }
            else {
                Picasso
                    .get()
                    .load(artists[0].strArtistLogo)
                    .into(artist_logo)
            }
            artist_biog.text = artists[0].strBiographyEN
            artist_name.text = artists[0].strArtist
            artist_year.text = artists[0].intBornYear.toString()
            if (artists[0].strWebsite != null) {
                artist_website.text = artists[0].strWebsite
            }
            else{
                artist_website.text = R.string.no_website.toString()
            }
            Log.d("taaaaaaaaag", artists[0].idArtist)
            user_albums.setOnClickListener {
                val intent = Intent(this, ArtistAlbum::class.java)
                intent.putExtra(Constants.ARTIST_NAME, artists[0].strArtist)
                startActivity(intent)
            }
            artist_videos.setOnClickListener {
                val intent = Intent(this, TrackDetail::class.java)
                intent.putExtra(Constants.ARTIST_ID, artists[0].idArtist)
                startActivity(intent)
            }

    }

    private fun addFav(artist: Artist){
        if (auth.currentUser != null) {
            add_fav.isEnabled = true
            add_fav.setOnCheckedChangeListener { buttonView, isChecked ->
                buttonView.setOnClickListener {
                    if (isChecked) {
                        db.collection(Constants.USER_COLLECTION)
                            .document(auth.currentUser!!.uid)
                            .update("artists", FieldValue.arrayUnion(artist))
                            .addOnSuccessListener {
                                Toast.makeText(this, "Added", Toast.LENGTH_LONG).show()
                            }
                            .addOnFailureListener {
                                Log.d("eeeerrrrr", it.localizedMessage)
                            }

                    } else {
                        db.collection(Constants.USER_COLLECTION)
                            .document(auth.currentUser!!.uid)
                            .update("artists", FieldValue.arrayRemove(artist))
                            .addOnSuccessListener {
                                Toast.makeText(this, "Removed", Toast.LENGTH_LONG).show()
                            }
                            .addOnFailureListener {
                                Log.d("eeeerrrrr", it.localizedMessage)
                            }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        if (auth.currentUser != null){
            menu!!.findItem(R.id.action_logout).isVisible = true;
            menu.findItem(R.id.action_fav).isVisible = true;
            menu.findItem(R.id.action_login).isVisible = false;
        }
        else if(auth.currentUser == null){
            menu!!.findItem(R.id.action_login).isVisible = true;
            menu.findItem(R.id.action_logout).isVisible = false;
            menu.findItem(R.id.action_fav).isVisible = false;
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.action_logout -> {
            logout()
            true
        }
        R.id.action_login ->{
            login()
            true
        }
        R.id.action_fav ->{
            goToFav()
            true
        }
        else -> false
    }
    private fun logout(){
        auth.signOut()
        finish()
        startActivity(intent)
    }
    private fun login(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun goToFav(){
        val intent = Intent(this, FavoutireArtist::class.java)
        startActivity(intent)
    }

}

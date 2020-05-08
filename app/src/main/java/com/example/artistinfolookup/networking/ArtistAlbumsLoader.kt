package com.example.artistinfolookup.networking


import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistAlbumsLoader (
    private val onSuccess: (ArtistAlbums) -> Unit,
    private val onError: (Throwable) -> Unit,
    private val key: String
){

    fun loadArtistAlbums(){
        ApiFactory.getApiService()
            .getAlbumByArtistName(key)
            .enqueue(object : Callback<ArtistAlbums> {
                override fun onResponse(
                    call: Call<ArtistAlbums>,
                    response: Response<ArtistAlbums>
                ) {
                    onSuccess(response.body()!!)
                    Log.d("taaaag0", "neggeeeeeee")

                }

                override fun onFailure(call: Call<ArtistAlbums>, t: Throwable) {
                    onError(t)
                }
            })
    }
}
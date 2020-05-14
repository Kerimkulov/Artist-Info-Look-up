package com.example.artistinfolookup.networking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VIdeoLoader (
    private val onSuccess: (ArtistVideo) -> Unit,
    private val onError: (Throwable) -> Unit,
    private val key: String
){

    fun loadVideo(){
        ApiFactory.getApiService()
            .getVideoByArtistId(key)
            .enqueue(object : Callback<ArtistVideo> {
                override fun onResponse(
                    call: Call<ArtistVideo>,
                    response: Response<ArtistVideo>
                ) {
                    onSuccess(response.body()!!)
                }

                override fun onFailure(call: Call<ArtistVideo>, t: Throwable) {
                    onError(t)
                }
            })
    }

}
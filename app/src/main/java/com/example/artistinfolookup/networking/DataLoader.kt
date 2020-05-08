package com.example.artistinfolookup.networking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataLoader (
    private val onSuccess: (ArtistInfo) -> Unit,
    private val onError: (Throwable) -> Unit,
    private val key: String
){

    fun loadData(){
        ApiFactory.getApiService()
            .getArtistByName(key)
            .enqueue(object : Callback<ArtistInfo> {
                override fun onResponse(
                    call: Call<ArtistInfo>,
                    response: Response<ArtistInfo>
                ) {
                    onSuccess(response.body()!!)
                }

                override fun onFailure(call: Call<ArtistInfo>, t: Throwable) {
                    onError(t)
                }
            })
    }

}
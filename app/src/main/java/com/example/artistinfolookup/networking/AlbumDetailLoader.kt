package com.example.artistinfolookup.networking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumDetailLoader (
    private val onSuccess: (AlbumDetail) -> Unit,
    private val onError: (Throwable) -> Unit,
    private val key: String
){

    fun loadAlbumDetail(){
        ApiFactory.getApiService()
            .getAlbumDetailByName(key)
            .enqueue(object : Callback<AlbumDetail> {
                override fun onResponse(
                    call: Call<AlbumDetail>,
                    response: Response<AlbumDetail>
                ) {
                    onSuccess(response.body()!!)
                }

                override fun onFailure(call: Call<AlbumDetail>, t: Throwable) {
                    onError(t)
                }
            })
    }
}
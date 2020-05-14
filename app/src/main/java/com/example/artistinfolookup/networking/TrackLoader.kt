package com.example.artistinfolookup.networking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackLoader (
    private val onSuccess: (TrackList) -> Unit,
    private val onError: (Throwable) -> Unit,
    private val key: String
){

    fun loadTracks(){
        ApiFactory.getApiService()
            .getTrackByAlbumId(key)
            .enqueue(object : Callback<TrackList> {
                override fun onResponse(
                    call: Call<TrackList>,
                    response: Response<TrackList>
                ) {
                    onSuccess(response.body()!!)
                }

                override fun onFailure(call: Call<TrackList>, t: Throwable) {
                    onError(t)
                }
            })
    }

}
package com.example.artistinfolookup.networking


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search.php")
    fun getArtistByName(@Query("s") key: String): Call<ArtistInfo>

    @GET("searchalbum.php")
    fun getAlbumByArtistName(@Query("s") key: String): Call<ArtistAlbums>

    @GET("searchalbum.php")
    fun getAlbumDetailByName(@Query("a") key: String): Call<AlbumDetail>

    @GET("track.php")
    fun getTrackByAlbumId(@Query("m") key: String): Call<TrackList>

    @GET("mvid.php")
    fun getVideoByArtistId(@Query("i") key: String): Call<ArtistVideo>


}
package com.example.artistinfolookup.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory{
    private const val ENDPOINT = "https://theaudiodb.com/api/v1/json/1/"

    fun getRetrofit() =
        Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getApiService() =
        getRetrofit().create(ApiService::class.java)
}
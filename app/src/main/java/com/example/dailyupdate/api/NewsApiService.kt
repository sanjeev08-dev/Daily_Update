package com.example.dailyupdate.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiService {
    fun create(): NewsApi {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NewsApi::class.java)
    }
}
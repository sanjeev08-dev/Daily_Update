package com.example.dailyupdate.Api

import com.example.dailyupdate.Data.NewsArticles
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    fun getNews(
        @Query("country") countryCode: String,
        @Query("category") newsType: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsArticles>

}
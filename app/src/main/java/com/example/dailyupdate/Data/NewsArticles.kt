package com.example.dailyupdate.Data

data class NewsArticles(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
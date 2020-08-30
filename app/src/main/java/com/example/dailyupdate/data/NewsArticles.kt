package com.example.dailyupdate.data

data class NewsArticles(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
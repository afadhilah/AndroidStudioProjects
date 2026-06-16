package com.aflabs.newsapp.data.repository

import com.aflabs.newsapp.data.api.RetrofitClient
import com.aflabs.newsapp.data.model.NewsResponse

class NewsRepository {
    private val apiKey = "94d191545c8e453b9e1230c3c23e6260"

    suspend fun getNews(): NewsResponse {
        return RetrofitClient.apiService.getTopHeadlines(country = "us", apiKey = apiKey)
    }

    suspend fun searchNews(query: String): NewsResponse {
        if (query.isBlank()) {
            return NewsResponse("ok", 0, emptyList())
        }
        return RetrofitClient.apiService.getEverything(query = query, apiKey = apiKey)
    }
}

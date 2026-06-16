package com.aflabs.newsapp.data.api

import com.aflabs.newsapp.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country: String = "us",
        @Query("apiKey")
        apiKey: String
    ): NewsResponse

    @GET("everything")
    suspend fun getEverything(
        @Query("q")
        query: String,
        @Query("apiKey")
        apiKey: String
    ): NewsResponse
}

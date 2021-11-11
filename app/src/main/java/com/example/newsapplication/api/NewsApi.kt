package com.task.newsapp.api

import com.task.newsapp.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines?sources=techcrunch&apiKey=063ebbfefe5a4085931ea08ec4ddc5d7")
    suspend fun getNews() : NewsResponse
}
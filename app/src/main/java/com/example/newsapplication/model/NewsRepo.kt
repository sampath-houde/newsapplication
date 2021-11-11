package com.task.newsapp.model

import com.task.newsapp.api.NewsApi
import com.task.newsapp.utils.BaseRepository

class NewsRepo(
    private val api: NewsApi
) : BaseRepository(){

    suspend fun getNews() = safeApiCall {
        api.getNews()
    }
}
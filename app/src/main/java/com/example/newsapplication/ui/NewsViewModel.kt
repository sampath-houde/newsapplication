package com.example.newsapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.newsapp.NewsResponse
import com.task.newsapp.model.NewsRepo
import com.task.newsapp.utils.ApiResponseHandler
import kotlinx.coroutines.launch


class NewsViewModel(private val repository: NewsRepo): ViewModel() {

    private val _news_list: MutableLiveData<ApiResponseHandler<NewsResponse>> =
        MutableLiveData()

    val newsList: LiveData<ApiResponseHandler<NewsResponse>>
        get() = _news_list

    fun getNews() = viewModelScope.launch {
        _news_list.value = repository.getNews()
    }
}
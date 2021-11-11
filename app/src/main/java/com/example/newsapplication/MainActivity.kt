package com.example.newsapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.ui.NewsAdapter
import com.example.newsapplication.ui.NewsViewModel
import com.task.newsapp.api.NewsApi
import com.task.newsapp.model.NewsRepo
import com.task.newsapp.utils.ApiResponseHandler
import com.task.newsapp.utils.RetrofitInstance
import com.task.newsapp.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window?.statusBarColor = ContextCompat.getColor(applicationContext, R.color.white)


        val api = RetrofitInstance.buildApi(NewsApi::class.java)
        val repo = NewsRepo(api)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)
        setContentView(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        callApi()

        binding.swipeRefresh.setOnRefreshListener {
            callApi()
        }
    }

    private fun callApi() {

        binding.swipeRefresh.isRefreshing = false
        binding.banner.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE

        binding.progressBar2.visibility = View.VISIBLE
        viewModel.getNews()
        viewModel.newsList.observe(this, {response->
            binding.progressBar2.visibility = View.GONE
            when(response) {
                is ApiResponseHandler.Success -> {
                    binding.banner.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    val adapter = NewsAdapter(response.value.articles, this)
                    binding.recyclerView.adapter = adapter
                }

                is ApiResponseHandler.Failure -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.banner.visibility = View.VISIBLE
                    if(response.isNetworkError) Toast.makeText(applicationContext, "Connection Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
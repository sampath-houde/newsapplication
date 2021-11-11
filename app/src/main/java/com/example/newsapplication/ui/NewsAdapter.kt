package com.example.newsapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.task.newsapp.Article

import android.content.Intent
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsapplication.MainActivity
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ViewNewsBinding


class NewsAdapter(private val list: List<Article>, private val mainActivity: MainActivity): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ViewNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                Glide.with(binding.newsPoster)
                    .load(this.urlToImage)
                    .placeholder(R.drawable.ic_baseline_search_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .transform(CenterCrop(), RoundedCorners(12))
                    .into(binding.newsPoster)
                binding.newsTitle.text = this.title
                binding.newsLayout.setOnClickListener {
                    val intent = Intent(mainActivity, NewsActivity::class.java)
                    intent.putExtra("NewsLink", this.url)
                    mainActivity.startActivity(intent)
                }
                binding.newsAuthor.text = this.author            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
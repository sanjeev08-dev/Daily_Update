package com.example.dailyupdate.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dailyupdate.Activities.DetailedNewsActivity
import com.example.dailyupdate.Data.Article
import com.example.dailyupdate.R
import com.example.dailyupdate.Utilities.Constants
import kotlinx.android.synthetic.main.news_recyclerview.view.*

class NewsAdapter(val newsData: List<Article>, val mContext: Context, val colorTheme: String?) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val newsPic = view.imageNews
        val newsHeading = view.textnewsHeading
        val readDetailedNews = view.readMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = newsData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setColorTheme(colorTheme, holder)
        holder.newsHeading.text = newsData[position].title
        Glide.with(holder.view.context)
            .load(newsData[position].urlToImage)
            .into(holder.newsPic)
        holder.readDetailedNews.setOnClickListener {
            val intent = Intent(mContext, DetailedNewsActivity::class.java)

            intent.putExtra(Constants.AUTHOR, newsData[position].author)
            intent.putExtra(Constants.DESCRIPTION, newsData[position].description)
            intent.putExtra(Constants.PUBLISHED_AT, newsData[position].publishedAt)
            intent.putExtra(Constants.TITLE, newsData[position].title)
            intent.putExtra(Constants.URL_TO_IMAGE, newsData[position].urlToImage)
            intent.putExtra(Constants.URL, newsData[position].url)

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            mContext.startActivity(intent)
        }
    }

    //set cardview background color
    private fun setColorTheme(colorTheme: String?, holder: ViewHolder) {
        when (colorTheme) {
            Constants.ORANGE -> {
                holder.newsHeading.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.colorPrimary
                    )
                )
                holder.readDetailedNews.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.colorPrimary
                    )
                )
            }
            Constants.RED -> {
                holder.newsHeading.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.red
                    )
                )
                holder.readDetailedNews.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.red
                    )
                )
            }
            Constants.BLUE -> {
                holder.newsHeading.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.blue
                    )
                )
                holder.readDetailedNews.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.blue
                    )
                )
            }
            Constants.GREEN -> {
                holder.newsHeading.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.green
                    )
                )
                holder.readDetailedNews.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.green
                    )
                )
            }
            Constants.YELLOW -> {
                holder.newsHeading.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.colorAccent
                    )
                )
                holder.readDetailedNews.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.colorAccent
                    )
                )
            }
            null -> {
                holder.newsHeading.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.colorPrimary
                    )
                )
                holder.readDetailedNews.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.colorPrimary
                    )
                )
            }
        }
    }
}
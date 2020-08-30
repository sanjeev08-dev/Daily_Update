package com.example.dailyupdate.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailyupdate.Adapter.NewsAdapter
import com.example.dailyupdate.Api.NewsApiService
import com.example.dailyupdate.Data.Article
import com.example.dailyupdate.Data.NewsArticles
import com.example.dailyupdate.R
import com.example.dailyupdate.Utilities.Constants
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsActivity : AppCompatActivity(), Callback<NewsArticles> {
    var code: String? = null
    val newsData = ArrayList<Article>()
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setThemeActivity(sharedPreferences.getString(Constants.COLOR_CODE, null))
        setContentView(R.layout.activity_news)
        setElementsColor(sharedPreferences.getString(Constants.COLOR_CODE, null))

        code = sharedPreferences.getString(Constants.COUNTRY_CODE, null)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)
        getCurrentPositionTypeNews(bottomNavigationView.currentActiveItemPosition)
        bottomNavigationView.setNavigationChangeListener { view, position ->
            getCurrentPositionTypeNews(position)
        }

        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.adapter = NewsAdapter(newsData, applicationContext,sharedPreferences.getString(Constants.COLOR_CODE, null))

        swipeRefreshLayout.setOnRefreshListener {
            getCurrentPositionTypeNews(bottomNavigationView.currentActiveItemPosition)
        }

    }


    private fun getCurrentPositionTypeNews(currentPosition: Int) {
        val newsType =
            arrayOf("business", "entertainment", "health", "science", "sports", "technology")
        getNews(newsType[currentPosition])
    }

    private fun getNews(newsType: String) {
        swipeRefreshLayout.isRefreshing = true
        val newsData = NewsApiService.create()
        newsData.getNews(code.toString(), newsType, Constants.MY_NEWS_KEY).enqueue(this)
    }

    override fun onFailure(call: Call<NewsArticles>, t: Throwable) {
        Log.e("Error", t.localizedMessage)
    }

    override fun onResponse(call: Call<NewsArticles>, response: Response<NewsArticles>) {
        val newsArticles = response.body()
        swipeRefreshLayout.isRefreshing = false
        if (newsArticles != null) {
            newsData.clear()
            newsData.addAll(newsArticles.articles)
            recyclerViewNews.adapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setElementsColor(color: String?) {
        when (color) {
            Constants.RED -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                bottomNavigationView.setBackgroundResource(R.drawable.navigationbgred)
            }
            Constants.ORANGE -> {
                toolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorPrimary
                    )
                )
                bottomNavigationView.setBackgroundResource(R.drawable.navigationbgorange)
            }
            Constants.BLUE -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.blue))
                bottomNavigationView.setBackgroundResource(R.drawable.navigationbgblue)
            }
            Constants.GREEN -> {
                toolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.green
                    )
                )
                bottomNavigationView.setBackgroundResource(R.drawable.navigationbggreen)
            }
            Constants.YELLOW -> {
                toolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorAccent
                    )
                )
                bottomNavigationView.setBackgroundResource(R.drawable.navigationbgyellow)
            }
            null -> {
                toolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorPrimary
                    )
                )
                bottomNavigationView.setBackgroundResource(R.drawable.navigationbgorange)
            }
        }
    }

    private fun setThemeActivity(color: String?) {
        when (color) {
            Constants.RED -> setTheme(R.style.RedTheme)
            Constants.ORANGE -> setTheme(R.style.OrangeTheme)
            Constants.BLUE -> setTheme(R.style.BlueTheme)
            Constants.GREEN -> setTheme(R.style.GreenTheme)
            Constants.YELLOW -> setTheme(R.style.YellowTheme)
            null -> setTheme(R.style.OrangeTheme)
        }
    }


}
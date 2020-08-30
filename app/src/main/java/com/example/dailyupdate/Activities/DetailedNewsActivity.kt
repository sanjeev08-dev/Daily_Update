package com.example.dailyupdate.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.dailyupdate.R
import com.example.dailyupdate.Utilities.Constants
import kotlinx.android.synthetic.main.activity_detailed_news.*
import kotlinx.android.synthetic.main.activity_news.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class DetailedNewsActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setThemeActivity(sharedPreferences.getString(Constants.COLOR_CODE, null))
        setContentView(R.layout.activity_detailed_news)
        setElementsColor(sharedPreferences.getString(Constants.COLOR_CODE, null))

        backImage.setOnClickListener {
            onBackPressed()
        }

        Glide.with(applicationContext).load(intent.getStringExtra(Constants.URL_TO_IMAGE)).into(newsImage)
        newsTitle.text = intent.getStringExtra(Constants.TITLE)

        val dateUTC = intent.getStringExtra(Constants.PUBLISHED_AT)

        if (dateUTC != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val instant = Instant.parse(dateUTC)
                val date = Date.from(instant)
                newsPublished.text = date.toString()
            }
        }
        authorName.text = intent.getStringExtra(Constants.AUTHOR)
        newsDescription.text = intent.getStringExtra(Constants.DESCRIPTION)
        val urlLink = intent.getStringExtra(Constants.URL)
        if (urlLink.equals(null)){
           url.visibility = View.GONE
        }else{
            url.visibility = View.VISIBLE
            url.text = urlLink
        }

        url.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlLink)
            startActivity(intent)
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
    private fun setElementsColor(color: String?) {
        when (color) {
            Constants.RED -> {
                authorName.setTextColor(ContextCompat.getColor(this,R.color.red))
            }
            Constants.ORANGE -> {
                authorName.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
            }
            Constants.BLUE -> {
                authorName.setTextColor(ContextCompat.getColor(this,R.color.blue))
            }
            Constants.GREEN -> {
                authorName.setTextColor(ContextCompat.getColor(this,R.color.green))
            }
            Constants.YELLOW -> {
                authorName.setTextColor(ContextCompat.getColor(this,R.color.colorAccent))
            }
            null -> {
                authorName.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
            }
        }
    }

}
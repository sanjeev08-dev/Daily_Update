package com.example.dailyupdate.activities

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyupdate.R
import com.example.dailyupdate.utilities.Constants
import com.example.dailyupdate.utilities.CountryCode
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var countryCode: String
    private lateinit var sharedPreferences: SharedPreferences

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        if (!sharedPreferences.getString(Constants.COUNTRY_CODE, null).isNullOrEmpty()) {
            startActivity(Intent(this, NewsActivity::class.java))
            finish()
        }

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels

        textCountyTitle.measure(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )

        circleImage.animate().translationX((width / 1.1).toFloat()).alpha(100F).duration = 2000
        textCountyTitle.animation = AnimationUtils.loadAnimation(
            this,
            R.anim.textcountyanim
        )
        countryCard.animation = AnimationUtils.loadAnimation(
            this,
            R.anim.cardanim
        )

        countrySpinner.setOnSpinnerItemSelectedListener<String> { _, text ->
            countryCode = CountryCode.getCountryCode(text).toString()
            continueButton.visibility = View.VISIBLE
        }

        continueButton.setOnClickListener {
            if (countryCode.isNotEmpty()) {
                val editor = sharedPreferences.edit()
                editor.apply { putString(Constants.COUNTRY_CODE, countryCode) }
                editor.apply { putString(Constants.COLOR_CODE, Constants.ORANGE) }
                editor.apply()
                startActivity(Intent(this, NewsActivity::class.java))
                finish()
            }
        }
    }
}
package com.example.dailyupdate.Activities

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyupdate.R
import com.example.dailyupdate.Utilities.Constants
import com.example.dailyupdate.Utilities.CountryCode
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var countryCode: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences =
            getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
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

        circleImage.animate().translationX((width / 1.1).toFloat()).alpha(100F).setDuration(2000)
        textCountyTitle.animation = AnimationUtils.loadAnimation(this,
            R.anim.textcountyanim
        )
        countryCard.animation = AnimationUtils.loadAnimation(this,
            R.anim.cardanim
        )

        countrySpinner.setOnSpinnerItemSelectedListener<String> { index, text ->
            countryCode = CountryCode.getCountryCode(text).toString()
            continueButton.visibility = View.VISIBLE
        }

        continueButton.setOnClickListener {
            if (!countryCode.isEmpty()) {
                val sharedPreferences =
                    getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply { putString(Constants.COUNTRY_CODE, countryCode) }
                editor.apply{putString(Constants.COLOR_CODE,Constants.ORANGE)}
                editor.apply()
                startActivity(Intent(this, NewsActivity::class.java))
                finish()
            }
        }
    }
}
package com.example.dailyupdate.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dailyupdate.R
import com.example.dailyupdate.Utilities.Constants
import com.example.dailyupdate.Utilities.CountryCode
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    var countryCode: String = ""
    var theme = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val sharedPreferences =
            getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val colors = arrayListOf(
            IconSpinnerItem(
                ContextCompat.getDrawable(applicationContext, R.drawable.orange),
                Constants.ORANGE
            ),
            IconSpinnerItem(
                ContextCompat.getDrawable(applicationContext, R.drawable.red),
                Constants.RED
            ),
            IconSpinnerItem(
                ContextCompat.getDrawable(applicationContext, R.drawable.blue),
                Constants.BLUE
            ),
            IconSpinnerItem(
                ContextCompat.getDrawable(applicationContext, R.drawable.yellow),
                Constants.YELLOW
            ),
            IconSpinnerItem(
                ContextCompat.getDrawable(applicationContext, R.drawable.green),
                Constants.GREEN
            )
        )

        countrySpinner.setOnSpinnerItemSelectedListener<String> { index, text ->
            countryCode = CountryCode.getCountryCode(text).toString()
        }
        //

        themeSpinner.apply {
            setSpinnerAdapter(IconSpinnerAdapter(this))
            setItems(colors)
            getSpinnerRecyclerView().layoutManager = GridLayoutManager(applicationContext, 2)
        }

        //
        themeSpinner.setOnSpinnerItemSelectedListener<IconSpinnerItem> { index, item ->
            theme = item.text as String
        }

        backImage.setOnClickListener {
            startActivity(Intent(this, NewsActivity::class.java))
            finish()
        }
        saveSettings.setOnClickListener {
            if (!(countrySpinner.selectedIndex < 0)) {
                editor.apply { putString(Constants.COUNTRY_CODE, countryCode) }
            }
            if (!(themeSpinner.selectedIndex < 0)) {
                editor.apply { putString(Constants.COLOR_CODE, theme) }
            }
            if (countrySpinner.selectedIndex < 0 && themeSpinner.selectedIndex < 0) {
                Toast.makeText(applicationContext, "Nothing to update", Toast.LENGTH_SHORT).show()
            }
            editor.apply()
            startActivity(Intent(this, NewsActivity::class.java))
            finish()
        }
    }
}
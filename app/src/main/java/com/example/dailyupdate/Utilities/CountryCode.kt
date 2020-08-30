package com.example.dailyupdate.Utilities

import java.util.*

object CountryCode {
    fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }?.toLowerCase(
            Locale.ROOT
        )
}
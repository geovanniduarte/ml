package com.geo.mercadolibre.product.util

import android.content.SharedPreferences
import com.geo.mercadolibre.base.Constants

class SiteManager(val sharedPreferences: SharedPreferences) {

    companion object {
        const val PREF_CURRENT_SITE = "current_site"
    }

    var currentSite: String
    get() = sharedPreferences.getString(PREF_CURRENT_SITE, Constants.DEFAULT_SITE) ?: Constants.DEFAULT_SITE
    set(value) {
        sharedPreferences.edit().putString(PREF_CURRENT_SITE, value).apply()
    }
}
package com.example.qrcodescanner.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    private const val PREF_NAME = "user_settings"
    private lateinit var prefs: SharedPreferences
//    private const val PREF_LANGUAGE = "app_language"


    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }




//    fun setAppLanguage(languageCode: String) {
//        prefs.edit().putString(PREF_LANGUAGE, languageCode).apply()
//    }
//
//    fun getAppLanguage(): String {
//        return prefs.getString(PREF_LANGUAGE, "en") ?: "en"
//    }

}
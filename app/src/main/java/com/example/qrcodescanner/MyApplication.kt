package com.example.qrcodescanner

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.qrcodescanner.utils.PreferenceHelper
import com.example.qrcodescanner.utils.updateLocale
import com.nined.snapshareapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }


        PreferenceHelper.init(this)

    }

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val langCode = prefs.getString("app_language", "en") ?: "en"
        val context = newBase.updateLocale(langCode)
        super.attachBaseContext(context)
    }


}
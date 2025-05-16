package com.example.qrcodescanner

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
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

    }
}
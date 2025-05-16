package com.nined.snapshareapp.di

import androidx.room.Room
import com.example.qrcodescanner.data.db.AppDatabase
import com.example.qrcodescanner.data.repo.QrCodeRepository
import com.example.qrcodescanner.viewmodel.QrCodeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module{


        single { AppDatabase.createDatabase(androidContext()).savedQrCodeDao() }

        single { Room.databaseBuilder(get(), AppDatabase::class.java, "saved_qr_database").build() }

        single { get<AppDatabase>().savedQrCodeDao() }

        single { QrCodeRepository(get()) }

        viewModel { QrCodeViewModel(get()) }
}





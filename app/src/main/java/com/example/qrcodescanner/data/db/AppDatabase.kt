package com.example.qrcodescanner.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.qrcodescanner.data.dao.SavedQrCodeDao
import com.example.qrcodescanner.data.entities.SavedQrCodeEntity


@Database(entities = [SavedQrCodeEntity::class], version = 1)

abstract class AppDatabase: RoomDatabase() {

    abstract fun savedQrCodeDao(): SavedQrCodeDao

    companion object {
        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "saved_qr_database"
            ).build()
        }
    }
}
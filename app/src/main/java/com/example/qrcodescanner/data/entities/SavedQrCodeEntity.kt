package com.example.qrcodescanner.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_qr_code")

data class SavedQrCodeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imagePath: String,
    val qrType: String,
    val content: String,
    val createdDate: String,
    val isBookmarked: Boolean,
    val generatedQrType: Int
)
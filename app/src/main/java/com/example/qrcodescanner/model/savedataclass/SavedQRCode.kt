package com.example.qrcodescanner.model.savedataclass

import android.graphics.Bitmap

data class SavedQRCode(
    val bitmap: Bitmap,
    val type: String,
    val data: String,
    val date: String
)

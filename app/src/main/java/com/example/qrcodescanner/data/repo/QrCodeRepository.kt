package com.example.qrcodescanner.data.repo

import com.example.qrcodescanner.data.dao.SavedQrCodeDao
import com.example.qrcodescanner.data.entities.SavedQrCodeEntity
import kotlinx.coroutines.flow.Flow

class QrCodeRepository(private val dao: SavedQrCodeDao) {
    suspend fun saveQrCode(qrCode: SavedQrCodeEntity) = dao.insertQRCode(qrCode)


    fun getCreatedQrCodes(): Flow<List<SavedQrCodeEntity>> = dao.getAllQrCodesByType(0)

    fun getScannedQrCodes(): Flow<List<SavedQrCodeEntity>> = dao.getAllQrCodesByType(1)

    fun getBookmarkedQrCodes(): Flow<List<SavedQrCodeEntity>> = dao.getBookmarkedQrCodes(true)

    suspend fun updateQrCode(qrCode: SavedQrCodeEntity) = dao.updateQrCode(qrCode)

    suspend fun deleteQrCode(qrCode: SavedQrCodeEntity) = dao.deleteQrCode(qrCode)
}
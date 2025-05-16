package com.example.qrcodescanner.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.qrcodescanner.data.entities.SavedQrCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedQrCodeDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertQRCode(qrCode: SavedQrCodeEntity)

    @Query("SELECT * FROM saved_qr_code where generatedQrType = :qrType ORDER BY id DESC")
    fun getAllQrCodesByType(qrType :Int): Flow<List<SavedQrCodeEntity>>


    @Query("SELECT * FROM saved_qr_code where isBookmarked = :bookmark ORDER BY id DESC")
    fun getBookmarkedQrCodes(bookmark :Boolean): Flow<List<SavedQrCodeEntity>>

    @Update
    suspend fun updateQrCode(qrCode: SavedQrCodeEntity)

    @Delete
    suspend fun deleteQrCode(qrCode: SavedQrCodeEntity)


}
package com.example.qrcodescanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.qrcodescanner.data.entities.SavedQrCodeEntity
import com.example.qrcodescanner.data.repo.QrCodeRepository
import kotlinx.coroutines.launch

class QrCodeViewModel(private val repository: QrCodeRepository): ViewModel(){

    fun saveQrCode(qrCode: SavedQrCodeEntity){
        viewModelScope.launch {
            repository.saveQrCode(qrCode)
        }
    }

    val createdQrCodes = repository.getCreatedQrCodes().asLiveData()
    val scannedQrCodes  = repository.getScannedQrCodes().asLiveData()
    val bookmarkedQrCodes = repository.getBookmarkedQrCodes().asLiveData()


    fun addedToBookmark(qrCode: SavedQrCodeEntity) {
        val updated = qrCode.copy(isBookmarked = !qrCode.isBookmarked)
        viewModelScope.launch {
            repository.updateQrCode(updated)
        }
    }

    fun deleteQrCode(qrCode: SavedQrCodeEntity) {
        viewModelScope.launch {
            repository.deleteQrCode(qrCode)
        }
    }

}
package com.example.qrcodescanner.data.sharedviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SharedViewModel : ViewModel() {
    private val _qrData = MutableLiveData<String>()
    val qrData: LiveData<String> = _qrData

    fun setQrData(data: String) {
        _qrData.value = data
    }
}

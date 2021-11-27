package com.maluku.sma_rt.ui.warga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WargaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Daftar Warga"
    }
    val text: LiveData<String> = _text
}
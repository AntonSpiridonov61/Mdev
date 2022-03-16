package com.example.wearher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel : ViewModel() {
    val data: MutableLiveData<DataWeather> by lazy {
        MutableLiveData<DataWeather>()
    }
}

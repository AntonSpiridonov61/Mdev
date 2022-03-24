package com.example.wearher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel : ViewModel() {
    val data: MutableLiveData<DataWeather> by lazy {
        MutableLiveData<DataWeather>()
    }
    val selectType: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val indexCity: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}

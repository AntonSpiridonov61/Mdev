package com.example.tabbedapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {
    var car = MutableLiveData<CarModel>()

    fun setCar(car_model: CarModel) {
        car.value = car_model
    }
}
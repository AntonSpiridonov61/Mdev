package com.example.auctiontrainer.screens.team

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamViewModel: ViewModel() {
    var viewState: MutableLiveData<Boolean> = MutableLiveData(false)
    var code: MutableLiveData<String> = MutableLiveData("")

    fun onChangeState() {
        when (viewState.value) {
            true -> viewState.value = false
            false -> viewState.value = true
            else -> viewState.value = false
        }
    }
}


package com.example.auctiontrainer.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LotsData: ViewModel() {
    var data: MutableLiveData<MutableList<LotModel>> =
        MutableLiveData(
            mutableListOf(LotModel("qwe", "type1", 90))
//            mutableListOf()
        )
}
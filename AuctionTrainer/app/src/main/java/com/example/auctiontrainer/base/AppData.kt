package com.example.auctiontrainer.base

import javax.inject.Inject


var lots = mutableListOf<LotModel>()

class AppData @Inject constructor() {

    fun getLots(): MutableList<LotModel> {
        return lots
    }

    fun addLots(lot: LotModel) {
        lots.add(lot)
    }
}
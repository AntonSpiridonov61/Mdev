package com.example.auctiontrainer.base

import javax.inject.Inject

val lots = mutableListOf<LotModel>()
val settingsRoom = SettingsRoom()

class AppData @Inject constructor() {

    fun getLots(): MutableList<LotModel> {
        return lots
    }

    fun addLots(lot: LotModel) {
        lots.add(lot)
    }
}

data class SettingsRoom(
    val time: String = "3 Минуты",
    val cntTeams: String = "2"
)
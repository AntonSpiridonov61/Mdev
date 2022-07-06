package com.example.auctiontrainer.base

import javax.inject.Inject

val lots = mutableListOf<LotModel>()
var settingsRoom = SettingsRoom()
var code: String = "0"
var nickname: String = ""

class AppData @Inject constructor() {

    fun getLots(): MutableList<LotModel> {
        return lots
    }

    fun addLots(lot: LotModel) {
        lots.add(lot)
    }

    fun deleteLots(lot: LotModel) {
        lots.remove(lot)
    }

    fun getSettings(): SettingsRoom {
        return settingsRoom
    }

    fun setSettings(time: String, cntTeams: String) {
        settingsRoom = SettingsRoom(time, cntTeams)
    }

    fun setCode(genCode: String) {
        code = genCode
    }

    fun getCode(): String {
        return code
    }

    fun setNickname(nick: String) {
        nickname = nick
    }

    fun getMyNickname(): String {
        return nickname
    }
}

data class SettingsRoom(
    val time: String = "3",
    val cntTeams: String = "2"
)
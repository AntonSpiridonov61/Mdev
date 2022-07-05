package com.example.auctiontrainer.database

import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.SettingsRoom

interface RoomsRepository {

    fun createRoom(code: String)

    fun nextLot(code: String, cntLots: Int)

    suspend fun whichRoom(code: String, onSuccess: () -> Unit, onFail: (String) -> Unit)

    suspend fun readAllLots(code: String, onSuccess: (List<LotModel>) -> Unit)

    suspend fun readOneLot(code: String, onSuccess: (LotModel) -> Unit)

    suspend fun readSettings(code: String, onSuccess: (SettingsRoom) -> Unit)
}
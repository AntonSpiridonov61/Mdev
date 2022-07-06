package com.example.auctiontrainer.database

import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.RoomModel
import com.example.auctiontrainer.base.SettingsRoom

interface RoomsRepository {

    fun createRoom(code: String)

    fun nextLot(code: String, cntLots: Int)

    fun setBet(code: String, nameLot: String, nameTeam: String, bet: Int)

    suspend fun connectToRoom(code: String, onSuccess: () -> Unit, onFail: (String) -> Unit)

    suspend fun readOneLot(code: String, onSuccess: (LotModel) -> Unit)

    suspend fun readRoom(code: String, onSuccess: (RoomModel) -> Unit)
}
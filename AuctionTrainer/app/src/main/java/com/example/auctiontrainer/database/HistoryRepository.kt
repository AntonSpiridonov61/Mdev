package com.example.auctiontrainer.database

import com.example.auctiontrainer.base.LotModel

interface HistoryRepository {
    fun setBet(code: String, nameLot: String, nameTeam: String, bet: Int)

    suspend fun readBets(code: String, onSuccess: (Map<String, Map<String, Int>>) -> Unit)
}
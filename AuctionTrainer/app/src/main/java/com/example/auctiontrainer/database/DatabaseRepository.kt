package com.example.auctiontrainer.database

import androidx.lifecycle.LiveData
import com.example.auctiontrainer.base.LotModel

interface DatabaseRepository {

    val readAll: LiveData<List<LotModel>>
    val readOne: LiveData<LotModel>

    fun connectToDatabase() {}

    suspend fun createRoom(code: String)
}
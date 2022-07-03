package com.example.auctiontrainer.database

import androidx.lifecycle.LiveData
import com.example.auctiontrainer.base.LotModel

interface DatabaseRepository {

    val readAll: LiveData<List<LotModel>>
    val readOne: LiveData<LotModel>

    fun signOut() {}
    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    )

    fun registration(
        email: String,
        password: String,
        nickname: String,
        role: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    )

    fun createUser(uid: String, nickname: String, role: String)
    suspend fun whoIsUser(uid: String, onSuccess: (String) -> Unit, onFail: (String) -> Unit)
    suspend fun createRoom(code: String)

}
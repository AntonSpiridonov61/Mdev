package com.example.auctiontrainer.database

import androidx.lifecycle.LiveData
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.SettingsRoom

interface DatabaseRepository {

    val readAll: LiveData<List<LotModel>>
    val readOne: LiveData<LotModel>

    fun signOut()

    fun signIn(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFail: (String) -> Unit
    )

    fun registration(
        email: String,
        password: String,
        nickname: String,
        role: String,
        onSuccess: (String) -> Unit,
        onFail: (String) -> Unit
    )

    fun createUser(uid: String, nickname: String, role: String)

    fun createRoom(code: String)

    fun readNickname(uid: String, role: String, onSuccess: (String) -> Unit, onFail: (String) -> Unit)

    suspend fun whoIsUser(uid: String, onSuccess: (String) -> Unit, onFail: (String) -> Unit)

    suspend fun whichRoom(code: String)

    suspend fun readAllLots(code: String, onSuccess: (List<LotModel>) -> Unit, onFail: (String) -> Unit)

    suspend fun readOneLot(code: String, onSuccess: (LotModel) -> Unit, onFail: (String) -> Unit)

    suspend fun readSettings(code: String, onSuccess: (SettingsRoom) -> Unit, onFail: (String) -> Unit)
}
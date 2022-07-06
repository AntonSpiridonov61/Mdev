package com.example.auctiontrainer.database

interface UsersRepository {
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

    fun readNickname(role: String, onSuccess: (String) -> Unit, onFail: (String) -> Unit)

    suspend fun whoIsUser(onSuccess: (String) -> Unit, onFail: (String) -> Unit)

}
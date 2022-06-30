package com.example.auctiontrainer.base


data class LotModel(
    val title: String = "Name",
    val type: String = "Тип 1",
    val price: Int = 10,
    val state: String = "Не начат"
)

sealed class LotState() {
    object Completed : LotState()
    object Current : LotState()
    object NotState : LotState()
}

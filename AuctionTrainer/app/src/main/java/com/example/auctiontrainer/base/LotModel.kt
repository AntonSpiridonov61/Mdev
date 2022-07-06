package com.example.auctiontrainer.base


data class LotModel(
    val title: String = "Название лота",
    val type: String = "Тип 1",
    val startPrice: Int = 10,
    val limitPrice: Int = 50,
    val state: String = "Не начат"
)

sealed class LotState() {
    object Completed : LotState()
    object Current : LotState()
    object NotState : LotState()
}

package com.example.auctiontrainer.base

data class Room(
    val setting: SettingsRoom,
    val lots: MutableList<LotModel>
)

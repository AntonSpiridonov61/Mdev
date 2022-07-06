package com.example.auctiontrainer.base

data class RoomModel(
    val lots: List<LotModel>,
    val bets: Map<String, Map<String, Int>>,
    val setting: SettingsRoom,
    val connectedTeams: Map<String, Boolean>,
    val currentLot: Int,
    val winners: Map<String, String>
)

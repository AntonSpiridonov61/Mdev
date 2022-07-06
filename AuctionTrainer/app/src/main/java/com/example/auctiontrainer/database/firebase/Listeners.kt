package com.example.auctiontrainer.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.RoomModel
import com.example.auctiontrainer.base.SettingsRoom
import com.example.auctiontrainer.base.code
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

fun listenerRoom(onSuccess: (RoomModel) -> Unit): ValueEventListener {
    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val lots = mutableListOf<LotModel>()
            val bets = mutableMapOf<String, List<Map<String, Int>>>()
            var setting = SettingsRoom()
            val connectedTeams = mutableMapOf<String, Boolean>()

            snapshot.child("lots").children.map {
                lots.add(it.getValue(LotModel::class.java)!!)
            }

            snapshot.child("bets").children.forEach {
                val nameLot = it.key
                val betsLot = mutableListOf<Map<String, Int>>()
                it.children.map { bet ->
                    betsLot.add(mapOf(bet.key!! to bet.getValue(Int::class.java)!!))
                }
                bets[nameLot!!] = betsLot
            }
            setting = snapshot.child("setting").getValue(SettingsRoom::class.java)!!

            snapshot.child("connectedTeams").children.map {
                connectedTeams[it.key!!] = it.getValue(Boolean::class.java)!!
            }

            val currentLot = snapshot.child("currentLot").getValue(Int::class.java)!!

            Log.d("connectTeams", connectedTeams.toString())

            onSuccess(
                RoomModel(
                    lots = lots,
                    bets = bets,
                    setting = setting,
                    connectedTeams = connectedTeams,
                    currentLot = currentLot
                )
            )
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }
    return listener
}

fun oneLots(onSuccess: (LotModel) -> Unit): ValueEventListener {

    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val idLot = snapshot.child("currentLot").getValue(Int::class.java)
            if (idLot == 0) {
                val lot = LotModel("-", "-", 0, 0,"")
                onSuccess(lot)
            } else {
                val lot = snapshot.child("lots").child("lot_$idLot").getValue(LotModel::class.java)!!
                onSuccess(lot)
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    return listener
}
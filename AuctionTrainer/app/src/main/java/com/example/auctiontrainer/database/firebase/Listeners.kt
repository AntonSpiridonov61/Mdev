package com.example.auctiontrainer.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.code
import com.google.firebase.database.*

fun allLots(onSuccess: (List<LotModel>) -> Unit): ValueEventListener {

    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val lots = mutableListOf<LotModel>()
            snapshot.children.map {
                lots.add(it.getValue(LotModel::class.java)!!)
            }
            onSuccess(lots)
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
                val lot = LotModel("-", "-", 0, "")
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

fun allBets(onSuccess: (Map<String, Map<String, Int>>) -> Unit): ValueEventListener {
    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val bets = mutableMapOf<String, Map<String, Int>>()

            snapshot.children.forEach {
                val nameLot = it.key
                it.children.map { bet ->
                    bets.put(nameLot!!, mapOf(bet.key!! to bet.getValue(Int::class.java)!!))
                }
            }
            onSuccess(bets)
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    return listener
}
package com.example.auctiontrainer.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.code
import com.google.firebase.database.*

class AllLostLiveData(): MutableLiveData<List<LotModel>>() {
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().reference
            .child("Rooms").child(code).child("lots")

    fun loadAllLots(code: String): List<LotModel> {
        val roomRef = database.child(code).child("lots")
        val lots = mutableListOf<LotModel>()

        roomRef.get().addOnSuccessListener {
            it.children.map { lot ->
                lots.add(lot.getValue(LotModel::class.java)!!)
                Log.d("firebase", lot.toString())
            }

            value = lots
        }

        return lots
    }

    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val lots = mutableListOf<LotModel>()
            snapshot.children.map {
                Log.d("firebase", it.toString())
                lots.add(it.getValue(LotModel::class.java)!!)
            }
            Log.d("firebase", lots.toString())
            value = lots
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    override fun onActive() {
        database.addValueEventListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        database.removeEventListener(listener)
        super.onInactive()
    }
}
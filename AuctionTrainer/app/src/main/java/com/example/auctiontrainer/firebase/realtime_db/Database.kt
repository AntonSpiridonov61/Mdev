package com.example.auctiontrainer.firebase.realtime_db


import android.util.Log
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.SettingsRoom
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
private val settingsRef: DatabaseReference = database.child("settingsRoom")
private val lotsRef: DatabaseReference = database.child("currentLots")

class RealTimeDatabase @Inject constructor() {

    fun setSettings(settings: SettingsRoom) {
        settingsRef.child("time").setValue(settings.time)
        settingsRef.child("cntTeams").setValue(settings.cntTeams)
    }

    fun setLots(lots: List<LotModel>) {
        lotsRef.removeValue()
        repeat(lots.size) {
            lotsRef.child("lot_$it").setValue(lots[it])
        }
    }

    fun setCode(code: String) {
        database.child("accessCode").setValue(code)
    }

    fun getLots() : MutableList<LotModel> {
        val lots = mutableListOf<LotModel>()
        lotsRef.get().addOnSuccessListener {
            if (it.exists()) {
                it.children.forEach { i ->
                    lots.add(i.getValue(LotModel::class.java)!!)
                }
            }
        }
        return lots
    }

    fun getCode() : MutableList<String> {
        val code = mutableListOf<String>("12423")
        database.child("accessCode").get().addOnSuccessListener {
            if (it.exists()) {
                code.add(it.getValue<String>()!!)
            }
        }
        return code
    }

    fun getSettings() : MutableList<SettingsRoom> {
        val set = mutableListOf<SettingsRoom>(SettingsRoom())
//        settingsRef.get().addOnSuccessListener {
//            if (it.exists()) {
//                set.add(it.getValue(SettingsRoom::class.java)!!)
//            }
//        }
        return set
    }
}

//myRef.addValueEventListener(object: ValueEventListener {
//    override fun onDataChange(dataSnapshot: DataSnapshot) {
//        val value = dataSnapshot.getValue<String>()
//        Log.d(TAG, "Value is: $value")
//    }
//
//    override fun onCancelled(error: DatabaseError) {
//        // Failed to read value
//        Log.w(TAG, "Failed to read value.", error.toException())
//    }
//}
//)
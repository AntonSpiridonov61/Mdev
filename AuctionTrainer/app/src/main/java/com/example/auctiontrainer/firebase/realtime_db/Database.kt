package com.example.auctiontrainer.firebase.realtime_db


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.auctiontrainer.base.AppData
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.Room
import com.example.auctiontrainer.base.SettingsRoom
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject


private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
private val roomsRef: DatabaseReference = database.child("Rooms")

class RealTimeDatabase @Inject constructor(
    val data: AppData
) {
    private var lots: MutableLiveData<MutableList<LotModel>> = MutableLiveData(mutableListOf())
    fun createRoom(code: String) {
        val room = roomsRef.child(code)
        room.child("currentLot").setValue(0)
        room.child("setting").setValue(data.getSettings())
        val lots = data.getLots()
        repeat(lots.size) {
            room.child("lots").child("lot_${it + 1}").setValue(lots[it])
        }
    }
}

//    fun getRoom(code: String): List<LotModel> {
//        loadRoom(code)
//        return lots.value!!
//    }
//
//    suspend fun loadRoom(code: String) {
//        try {
//            lots = roomsRef.child(code).child("lots").get().await()
//        } catch (e: Exception) {
//
//        }
//        lots = roomsRef.child(code).child("lots").get().await()
//            .addOnSuccessListener {
//            if (it.exists()) {
//                return@addOnSuccessListener
//            }
//        }.addOnCanceledListener {
//            return@addOnCanceledListener
//        }
//    }

//    fun getSettings() : MutableList<SettingsRoom> {
//        val set = mutableListOf<SettingsRoom>(SettingsRoom())
//        settingsRef.get().addOnSuccessListener {
//            if (it.exists()) {
//                set.add(it.getValue(SettingsRoom::class.java)!!)
//            }
//        }.addOnCanceledListener {
//
//        }
//        return set
//    }

//    val postListener = object : ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            // Get Post object and use the values to update the UI
//            val post = dataSnapshot.getValue<String>()
//            // ...
//        }
//
//        override fun onCancelled(databaseError: DatabaseError) {
//            // Getting Post failed, log a message
//            Log.w("loadPost:onCancelled", databaseError.toException())
//        }
//    }
//}

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
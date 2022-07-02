package com.example.auctiontrainer.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.code
import com.example.auctiontrainer.database.DatabaseRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Collections.copy

class AppFirebaseRepository: DatabaseRepository {
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().reference
            .child("Rooms")
    override val readAll: LiveData<List<LotModel>> = AllLostLiveData()
    override val readOne: LiveData<LotModel>
        get() = TODO("Not yet implemented")

    fun readAllLots(code: String): MutableList<LotModel> {
        val roomRef = database.child(code).child("lots")
        val lots = mutableListOf<LotModel>()

        roomRef.get().addOnSuccessListener {
            it.children.map { lot ->
                lots.add(lot.getValue(LotModel::class.java)!!)
                Log.d("firebase", lot.toString())
            }
        }

        return lots
    }

    override suspend fun createRoom(code: String) {
        TODO("Not yet implemented")
    }

    override fun connectToDatabase() {
        super.connectToDatabase()
    }
}
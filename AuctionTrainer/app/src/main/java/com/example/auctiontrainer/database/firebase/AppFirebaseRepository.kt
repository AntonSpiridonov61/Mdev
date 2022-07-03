package com.example.auctiontrainer.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.code
import com.example.auctiontrainer.database.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Collections.copy
import javax.inject.Inject

class AppFirebaseRepository @Inject constructor(): DatabaseRepository {
    private val mAuth = FirebaseAuth.getInstance()
    private val dbUsers = FirebaseDatabase.getInstance().reference.child("users")
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

    override fun createRoom(code: String) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun signIn(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFail: (String) -> Unit
    ) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess(it.user!!.uid)
            }
            .addOnFailureListener {
                onFail(it.message.toString())
            }
    }

    override fun registration(
        email: String,
        password: String,
        nickname: String,
        role: String,
        onSuccess: (String) -> Unit,
        onFail: (String) -> Unit
    ) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                createUser(it.user!!.uid, nickname, role)
                onSuccess(it.user!!.uid)
            }
            .addOnFailureListener {
                onFail(it.message.toString())
            }
    }

    override fun createUser(uid: String, nickname: String, role: String) {
        when (role) {
            "Организатор" -> {
                dbUsers.child("organizers").child(uid).child("nickname").setValue(nickname)
            }
            "Команда" -> {
                dbUsers.child("teams").child(uid).child("nickname").setValue(nickname)
            }
        }
    }

    override suspend fun whoIsUser(
        uid: String,
        onSuccess: (String) -> Unit,
        onFail: (String) -> Unit
    ) {
        dbUsers.child("organizers").child(uid).get()
            .addOnSuccessListener {
                Log.d("who", it.toString())
                if (it.value != null) {
                    onSuccess("organizer")
                } else {
                    dbUsers.child("teams").child(uid).get()
                        .addOnSuccessListener { it1 ->
                            Log.d("who", it1.toString())
                            if (it1.value != null) {
                                onSuccess("team")
                            }
                        }
                        .addOnFailureListener {
                            onFail(it.message.toString())
                        }
                }
            }.addOnFailureListener {
                dbUsers.child("teams").child(uid).get()
                    .addOnSuccessListener {
                        Log.d("who", it.toString())
                        if (it.exists()) {
                            onSuccess("team")
                        }
                    }
                    .addOnFailureListener {
                        onFail(it.message.toString())
                    }
            }

    }
}
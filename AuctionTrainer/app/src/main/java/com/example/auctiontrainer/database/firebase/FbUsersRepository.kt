package com.example.auctiontrainer.database.firebase

import android.util.Log
import com.example.auctiontrainer.database.UsersRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class FbUsersRepository @Inject constructor(): UsersRepository {

    private val mAuth = FirebaseAuth.getInstance()
    private val dbUsers = FirebaseDatabase.getInstance().reference.child("users")

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

    override fun readNickname(uid: String, role: String, onSuccess: (String) -> Unit, onFail: (String) -> Unit) {
        dbUsers.child(role).child(uid).get()
            .addOnSuccessListener {
                Log.d("nick", it.toString())
                if (it.exists()) {
                    val nick = it.child("nickname").getValue(String::class.java)!!
                    onSuccess(nick)
                }
            }.addOnFailureListener {
                onFail(it.message.toString())
            }
    }
}
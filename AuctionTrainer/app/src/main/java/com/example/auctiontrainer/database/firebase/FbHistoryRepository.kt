package com.example.auctiontrainer.database.firebase

import com.example.auctiontrainer.database.HistoryRepository
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class FbHistoryRepository @Inject constructor(): HistoryRepository {

    private val dbHistory = FirebaseDatabase.getInstance().reference.child("history")

    override fun setBet(code: String, nameLot: String, nameTeam: String, bet: Int) {
        dbHistory.child(code).child(nameLot).child(nameTeam).setValue(bet)
    }

    override suspend fun readBets(
        code: String,
        onSuccess: (Map<String, Map<String, Int>>) -> Unit
    ) {
        dbHistory.child(code).addValueEventListener(allBets(onSuccess))
    }
}
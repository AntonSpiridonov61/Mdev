package com.example.auctiontrainer.database.firebase

import android.util.Log
import com.example.auctiontrainer.base.AppData
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.RoomModel
import com.example.auctiontrainer.database.RoomsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class FbRoomsRepository @Inject constructor(
    private val data: AppData,
    private val usersRepository: FbUsersRepository
): RoomsRepository {

    private val dbRooms = FirebaseDatabase.getInstance().reference.child("rooms")
    private val mAuth = FirebaseAuth.getInstance()

    override fun createRoom(code: String) {
        val room = dbRooms.child(code)
        room.child("currentLot").setValue(0)
        room.child("setting").setValue(data.getSettings())
        val lots = data.getLots()
        repeat(lots.size) {
            room.child("lots").child("lot_${it + 1}").setValue(lots[it])
        }
    }

//    override suspend fun readAllLots(
//        code: String,
//        onSuccess: (List<LotModel>) -> Unit
//    ) {
//        dbRooms.child(code).child("lots").addValueEventListener(allLots(onSuccess))
//    }

    override suspend fun readOneLot(
        code: String,
        onSuccess: (LotModel) -> Unit
    ) {
        dbRooms.child(code).addValueEventListener(oneLots(onSuccess))
    }

    override suspend fun readRoom(code: String, onSuccess: (RoomModel) -> Unit) {
        dbRooms.child(code).addValueEventListener(listenerRoom(onSuccess))
    }

    override suspend fun connectToRoom(
        code: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        dbRooms.child(code).get().addOnSuccessListener {
            if (it.exists()) {
                val uid = mAuth.currentUser?.uid
                if (uid != null){
                    usersRepository.readNickname(
                        "teams",
                        { nick ->
                            dbRooms.child(code).child("connectedTeams").child(nick).setValue(false)
                            onSuccess()
                        },
                        { onFail("error connect to room") }
                    )
                }
            } else {
                onFail("Такой комнаты не найдено")
            }
        }.addOnFailureListener {
            onFail(it.message.toString())
        }
    }

    private fun setState(code: String, curLotId: Int, state: String) {
        dbRooms.child(code)
            .child("lots")
            .child("lot_$curLotId")
            .child("state")
            .setValue(state)
    }

    private fun newLotForTeams(code: String) {
        dbRooms.child(code).child("connectedTeams").get().addOnSuccessListener {
            it.children.forEach { team ->
                dbRooms.child(code).child("connectedTeams").child(team.key!!).setValue(true)
            }
        }
    }

    override fun nextLot(code: String, cntLots: Int) {
        var curLotId = 0
        dbRooms.child(code).child("currentLot").get().addOnSuccessListener {
            it.getValue(Int::class.java)?.let { id ->
                curLotId = id
            }

            curLotId = curLotId.plus(1)
            Log.d("curLotId", curLotId.toString())
            if (curLotId <= cntLots) {
                dbRooms.child(code).child("currentLot").setValue(curLotId)
                newLotForTeams(code)
                setState(code, curLotId, "Текущий")

                if (curLotId > 1) {
                    setState(code, curLotId - 1, "Завершен")
                }
            } else {
                if (curLotId > cntLots) {
                    setState(code, curLotId - 1, "Завершен")
                }
            }
        }
    }

    override fun setBet(code: String, nameLot: String, nameTeam: String, bet: Int) {
        dbRooms.child(code).child("bets").child(nameLot).child(nameTeam).setValue(bet)
        dbRooms.child(code).child("connectedTeams").child(nameTeam).setValue(false)
    }
}
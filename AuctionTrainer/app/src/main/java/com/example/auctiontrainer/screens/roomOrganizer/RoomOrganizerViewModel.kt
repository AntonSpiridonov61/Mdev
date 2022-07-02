package com.example.auctiontrainer.screens.roomOrganizer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctiontrainer.base.AppData
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.SettingsRoom
import com.example.auctiontrainer.database.firebase.AppFirebaseRepository
import com.google.firebase.database.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RoomEvent() {
    object NextLotClick : RoomEvent()
    object EnterScreen : RoomEvent()
}

sealed class RoomViewState() {
    object Loading : RoomViewState()
    data class MainDisplay(
        val lots: MutableList<LotModel>,
        val code: String,
        val settings: SettingsRoom
    ) : RoomViewState()
    object ShowLot : RoomViewState()
}

@HiltViewModel
class RoomOrganizerViewModel @Inject constructor(
    private val data: AppData,
): ViewModel(), EventHandler<RoomEvent> {

    private val _roomViewState: MutableLiveData<RoomViewState> = MutableLiveData(RoomViewState.Loading)
    val roomViewState: LiveData<RoomViewState> = _roomViewState

    override fun obtainEvent(event: RoomEvent) {
        when(val currentState = _roomViewState.value) {
            is RoomViewState.Loading -> reduce(event, currentState)
            is RoomViewState.MainDisplay -> reduce(event, currentState)
            RoomViewState.ShowLot -> {}
        }
    }

    private fun reduce(event: RoomEvent, currentState: RoomViewState.MainDisplay) {
        when (event) {
            RoomEvent.NextLotClick -> nextLot(currentState)
            RoomEvent.EnterScreen -> loadData()
        }
    }

    private fun reduce(event: RoomEvent, currentState: RoomViewState.Loading) {
        when (event) {
            RoomEvent.EnterScreen -> loadData()
        }
    }

    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().reference
            .child("Rooms")

    fun readAllLots() = AppFirebaseRepository().readAll

    private fun loadData() {
        viewModelScope.launch(IO) {
            try {
                val code = data.getCode()
                val settings = data.getSettings()
                val lots1 = readAllLots()

                val lots = mutableListOf<LotModel>()


                val listener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.map {
                            Log.d("firebase", it.toString())
                            lots.add(it.getValue(LotModel::class.java)!!)
                        }

                        _roomViewState.postValue(
                            RoomViewState.MainDisplay(lots, code, settings)
                        )
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                }
                database.child(data.getCode()).child("lots").addValueEventListener(listener)

                _roomViewState.postValue(
                    RoomViewState.MainDisplay(lots, code, settings)
                )

//                roomRef.get().addOnSuccessListener {
//                    it.children.map { lot ->
//                        lots.add(lot.getValue(LotModel::class.java)!!)
////                        Log.d("firebase", lot.toString())
//                    }
//
//                    val lots1 = AppFirebaseRepository().readAll
//
//                    _roomViewState.postValue(
//                        RoomViewState.MainDisplay(lots1, code, settings)
//                    )
//                }
            } catch (e: Exception) {
                Log.e("load", e.toString())
            }
        }
    }

    private fun nextLot(currentState: RoomViewState.MainDisplay) {
        val code = data.getCode()
        var curLotId = 0
        database.child(code).child("currentLot").get().addOnSuccessListener {
            it.getValue(Int::class.java)?.let { it ->
                curLotId = it
            }

            curLotId = curLotId.plus(1)
            Log.d("curLotId", curLotId.toString())
            if (curLotId < currentState.lots.size) {
                database.child(code).child("currentLot").setValue(curLotId)
                database.child(code)
                    .child("lots")
                    .child("lot_$curLotId")
                    .child("state")
                    .setValue("Текущий")

                if (curLotId > 1) {
                    database.child(code)
                        .child("lots")
                        .child("lot_${curLotId - 1}")
                        .child("state")
                        .setValue("Завершен")
                }
            } else if (curLotId == currentState.lots.size) {
                database.child(code)
                    .child("lots")
                    .child("lot_${curLotId}")
                    .child("state")
                    .setValue("Завершен")
            }
//            loadData()
        }
    }
}
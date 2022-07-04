package com.example.auctiontrainer.screens.roomOrganizer

import android.util.Log
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RoomEvent() {
    object NextLotClick : RoomEvent()
    object EnterScreen : RoomEvent()
    data class ExpandLot(val id: Int) : RoomEvent()
}

sealed class RoomViewState() {
    object Loading : RoomViewState()
    data class MainDisplay(
        val lots: List<LotModel>,
        val lotsExpand: MutableList<Boolean>,
        val code: String,
        val settings: SettingsRoom
    ) : RoomViewState()
}

@HiltViewModel
class RoomOrganizerViewModel @Inject constructor(
    private val data: AppData,
    private val firebaseDatabase: AppFirebaseRepository
): ViewModel(), EventHandler<RoomEvent> {

    private val _roomViewState: MutableLiveData<RoomViewState> = MutableLiveData(RoomViewState.Loading)
    val roomViewState: LiveData<RoomViewState> = _roomViewState


    override fun obtainEvent(event: RoomEvent) {
        when(val currentState = _roomViewState.value) {
            is RoomViewState.Loading -> reduce(event, currentState)
            is RoomViewState.MainDisplay -> reduce(event, currentState)
            else -> {}
        }
    }

    private fun reduce(event: RoomEvent, currentState: RoomViewState.MainDisplay) {
        when (event) {
            RoomEvent.NextLotClick -> nextLot(currentState)
            is RoomEvent.ExpandLot -> {
                Log.d("exp", event.id.toString())
                val newEl = !currentState.lotsExpand[event.id]
                currentState.lotsExpand[event.id] = newEl
                _roomViewState.postValue(
                    RoomViewState.MainDisplay(
                        currentState.lots,
                        currentState.lotsExpand,
                        currentState.code,
                        currentState.settings
                    )
//                    currentState.copy(lotsExpand = currentState.lotsExpand)
                )
            }
            RoomEvent.EnterScreen -> loadData()
        }
    }

    private fun reduce(event: RoomEvent, currentState: RoomViewState.Loading) {
        when (event) {
            RoomEvent.EnterScreen -> loadData()
            else -> {}
        }
    }

    private fun loadData() {
        viewModelScope.launch(IO) {
            try {
                val code = data.getCode()
                val settings = data.getSettings()
                val lotsExpand = mutableListOf<Boolean>()

                firebaseDatabase.readAllLots(
                    code = code,
                    onSuccess =
                    {
                        if (lotsExpand.isEmpty()) {
                            repeat(it.size) {
                                lotsExpand.add(false)
                            }
                        }

                        _roomViewState.postValue(
                            RoomViewState.MainDisplay(it, lotsExpand, code, settings)
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("load", e.toString())
            }
        }
    }

    private fun nextLot(currentState: RoomViewState.MainDisplay) {
        firebaseDatabase.nextLot(data.getCode(), currentState.lots.size)
    }
}
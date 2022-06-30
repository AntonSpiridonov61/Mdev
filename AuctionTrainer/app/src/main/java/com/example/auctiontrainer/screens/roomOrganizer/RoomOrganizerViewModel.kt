package com.example.auctiontrainer.screens.roomOrganizer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.SettingsRoom
import com.example.auctiontrainer.firebase.realtime_db.RealTimeDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RoomEvent() {
    object NextLotClick : RoomEvent()
    object EnterScreen : RoomEvent()
}

sealed class RoomViewState() {
    object Loading : RoomViewState()
    data class MainDisplay(
        val lots: List<LotModel>,
        val code: List<String>,
        val settings: List<SettingsRoom>
    ) : RoomViewState()
    object ShowLot : RoomViewState()
}

@HiltViewModel
class RoomOrganizerViewModel @Inject constructor(
    private val db: RealTimeDatabase
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
            RoomEvent.NextLotClick -> {}
        }
    }

    private fun reduce(event: RoomEvent, currentState: RoomViewState.Loading) {
        when (event) {
            RoomEvent.EnterScreen -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val lots = db.getLots()
                val code = db.getCode()
                val settings = db.getSettings()
                Log.d("getC", code.toString())
                if (code.isNotEmpty()) {
                    _roomViewState.postValue(
                        RoomViewState.MainDisplay(lots, code, settings)
                    )
                } else {
                    loadData()
                }

            } catch (e: Exception) {
                Log.e("load", e.toString())
            }
        }
    }
}
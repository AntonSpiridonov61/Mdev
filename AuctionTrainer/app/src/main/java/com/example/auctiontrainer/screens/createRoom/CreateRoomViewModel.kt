package com.example.auctiontrainer.screens.createRoom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auctiontrainer.base.AppData
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.screens.createLot.CreateLotEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class CreateRoomViewState {
    data class Settings(
        val time: String,
        val cntTeams: String
    ) : CreateRoomViewState()
    data class Display(
        val items: MutableList<LotModel>
    ) : CreateRoomViewState()
    object NoItems: CreateRoomViewState()
}

sealed class CreateRoomEvent {
    object EnterScreen : CreateRoomEvent()
    data class TimeSelected(val newValue: String) : CreateRoomEvent()
    data class CntTeamSelected(val newValue: String) : CreateRoomEvent()
}

@HiltViewModel
class CreateRoomViewModel @Inject constructor (
    private val data: AppData
): ViewModel(), EventHandler<CreateRoomEvent> {

    private val _createRoomViewState: MutableLiveData<CreateRoomViewState> = MutableLiveData(CreateRoomViewState.NoItems)
    val createRoomViewState: LiveData<CreateRoomViewState> = _createRoomViewState

    override fun obtainEvent(event: CreateRoomEvent) {
        when (val currentState = _createRoomViewState.value) {
            is CreateRoomViewState.Display -> fetchLot()
            is CreateRoomViewState.NoItems -> fetchLot()
            is CreateRoomViewState.Settings -> reduce(event, currentState)
        }
    }

    private fun reduce(event: CreateRoomEvent, currentState: CreateRoomViewState.Settings) {
        when (event) {
            is CreateRoomEvent.TimeSelected -> _createRoomViewState.postValue(
                currentState.copy(time = event.newValue)
            )
            is CreateRoomEvent.CntTeamSelected -> _createRoomViewState.postValue(
                currentState.copy(cntTeams = event.newValue)
            )
        }
        fetchLot()
    }

    private fun fetchLot() {

        val lots = data.getLots()

        Log.d("LotR", lots.toString())
        if (lots.isEmpty()) {
            Log.d("LotE", lots.toString())
            _createRoomViewState.postValue(CreateRoomViewState.NoItems)
        } else {
            _createRoomViewState.postValue(
                CreateRoomViewState.Display(lots)
            )
        }
    }
}
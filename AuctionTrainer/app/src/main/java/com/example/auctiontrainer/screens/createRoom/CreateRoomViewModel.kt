package com.example.auctiontrainer.screens.createRoom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctiontrainer.base.AppData
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.SettingsRoom
import com.example.auctiontrainer.firebase.realtime_db.RealTimeDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

sealed class CreateRoomViewState {
    data class Display(
        val items: MutableList<LotModel>,
        val settings: SettingsRoom
    ) : CreateRoomViewState()

    object NoItems: CreateRoomViewState()
    object Success: CreateRoomViewState()
}

sealed class CreateRoomEvent {
    object EnterScreen : CreateRoomEvent()
    object SaveClick : CreateRoomEvent()
    data class TimeSelected(val newValue: String) : CreateRoomEvent()
    data class CntTeamSelected(val newValue: String) : CreateRoomEvent()
}

@HiltViewModel
class CreateRoomViewModel @Inject constructor (
    private val data: AppData,
    private val db: RealTimeDatabase
): ViewModel(), EventHandler<CreateRoomEvent> {

    private val _createRoomViewState: MutableLiveData<CreateRoomViewState> = MutableLiveData(CreateRoomViewState.NoItems)
    val createRoomViewState: LiveData<CreateRoomViewState> = _createRoomViewState

    override fun obtainEvent(event: CreateRoomEvent) {
        when (val currentState = _createRoomViewState.value) {
            is CreateRoomViewState.Display -> reduce(event, currentState)
            is CreateRoomViewState.NoItems -> fetchLot(currentState)
        }
    }

    private fun reduce(event: CreateRoomEvent, currentState: CreateRoomViewState.Display) {
        when (event) {
            CreateRoomEvent.EnterScreen -> fetchLot(currentState)
            CreateRoomEvent.SaveClick -> saveData(currentState)
            is CreateRoomEvent.TimeSelected -> {
                val curCnt = currentState.settings.cntTeams
                _createRoomViewState.postValue(
                    currentState.copy(settings = SettingsRoom(event.newValue, curCnt))
                )
            }
            is CreateRoomEvent.CntTeamSelected -> {
                val curTime = currentState.settings.time
                _createRoomViewState.postValue(
                    currentState.copy(settings = SettingsRoom(curTime, event.newValue))
                )
            }
        }
    }

    private fun fetchLot(currentState: CreateRoomViewState) {
        val lots = data.getLots()
        Log.d("LotR", lots.toString())
        if (lots.isEmpty()) {
            Log.d("LotE", lots.toString())
            _createRoomViewState.postValue(CreateRoomViewState.NoItems)
        } else {
            when (currentState) {
                is CreateRoomViewState.Display -> {
                    val curTime = currentState.settings.time
                    val curCnt = currentState.settings.cntTeams
                    _createRoomViewState.postValue(
                        CreateRoomViewState.Display(lots, SettingsRoom(curTime, curCnt))
                    )
                }
                else -> _createRoomViewState.postValue(
                    CreateRoomViewState.Display(lots, SettingsRoom())
                )
            }
        }
    }

    private fun saveData(currentState: CreateRoomViewState.Display) {
        viewModelScope.launch {
            try {
                db.setLots(currentState.items)
                db.setSettings(currentState.settings)
                db.setCode(generationCode())
                _createRoomViewState.postValue(CreateRoomViewState.Success)
            } catch (e: Exception) {
                Log.e("save", e.toString())
            }
        }
    }

    private fun generationCode() : String {
        return Random.nextInt(10000, 99999).toString()
    }
}
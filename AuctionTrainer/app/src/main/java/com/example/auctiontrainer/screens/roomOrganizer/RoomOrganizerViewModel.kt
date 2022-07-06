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
import com.example.auctiontrainer.database.firebase.FbRoomsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RoomEvent() {
    object NextLotClick : RoomEvent()
    object ExodusClick : RoomEvent()
    object EnterScreen : RoomEvent()
    data class ExpandLot(val id: Int) : RoomEvent()
}

sealed class RoomViewState() {
    object Loading : RoomViewState()
    data class MainDisplay(
        val lots: List<LotModel>,
        val lotsExpand: MutableList<Boolean>,
        val allBets: Map<String, Map<String, Int>>,
        val code: String,
        val settings: SettingsRoom,
        val connectedTeams: Map<String, Boolean>,
        val currentLot: Int,
        val winners: Map<String, String>
    ) : RoomViewState()
}

@HiltViewModel
class RoomOrganizerViewModel @Inject constructor(
    private val data: AppData,
    private val roomsRepository: FbRoomsRepository,
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
            RoomEvent.ExodusClick -> whoIsWinner(currentState)
            is RoomEvent.ExpandLot -> {
                Log.d("exp", event.id.toString())
                val newEl = !currentState.lotsExpand[event.id]
                currentState.lotsExpand[event.id] = newEl
                _roomViewState.postValue(
                    currentState.copy(lotsExpand = currentState.lotsExpand)
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
                val lotsExpand = mutableListOf<Boolean>()

                roomsRepository.readRoom(
                    code = code,
                    onSuccess =
                    {
                        if (lotsExpand.isEmpty()) {
                            repeat(it.lots.size) {
                                lotsExpand.add(false)
                            }
                        }

                        _roomViewState.postValue(
                            RoomViewState.MainDisplay(
                                it.lots,
                                lotsExpand,
                                it.bets,
                                code,
                                it.setting,
                                it.connectedTeams,
                                it.currentLot,
                                it.winners
                            )
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("load", e.toString())
            }
        }
    }

    private fun nextLot(currentState: RoomViewState.MainDisplay) {
        roomsRepository.nextLot(data.getCode(), currentState.lots.size)
    }

    private fun whoIsWinner(currentState: RoomViewState.MainDisplay) {
        val idLot = currentState.currentLot - 1
        val teamsForNextRound = mutableListOf<String>()

        val bets = currentState.allBets[currentState.lots[idLot].title]

        Log.d("whoIsWinner", bets.toString())
//        Log.d("bets first", bets.first().toString())

        if (!bets.isNullOrEmpty()) {
            bets.forEach {
                if (it.value == currentState.lots[idLot].limitPrice) {
                    teamsForNextRound.add(it.key)
                }
            }

            if (teamsForNextRound.isEmpty()) {
                val maxBet = bets.maxByOrNull { it.value }?.value ?: -1
                bets.forEach {
                    if (maxBet - it.value <= 5) {
                        teamsForNextRound.add(it.key)
                    }
                }
            }

            if (teamsForNextRound.size == 1) {
                Log.d("Winner", "${currentState.lots[idLot].title} -> ${teamsForNextRound[0]}")
                roomsRepository.addWinner(currentState.code, currentState.lots[idLot].title, teamsForNextRound[0])
            } else {
                roomsRepository.nextRoundForTeam(currentState.code, teamsForNextRound)
            }
        }
    }
}
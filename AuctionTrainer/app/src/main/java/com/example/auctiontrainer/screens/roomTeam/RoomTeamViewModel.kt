package com.example.auctiontrainer.screens.roomTeam

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctiontrainer.base.AppData
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.SettingsRoom
import com.example.auctiontrainer.database.firebase.FbRoomsRepository
import com.example.auctiontrainer.database.firebase.FbUsersRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RoomTeamEvent {
    data class BetChanged(val newValue: String) : RoomTeamEvent()
    object ChangeDialogState : RoomTeamEvent()
    object MakeBetClicked : RoomTeamEvent()
    object AllPayClicked : RoomTeamEvent()
    object PassClicked : RoomTeamEvent()
    object LoadData : RoomTeamEvent()
}

sealed class RoomTeamViewState {
    object WaitStart : RoomTeamViewState()
    object Loading : RoomTeamViewState()
    data class DisplayLot (
        val lots: List<LotModel>,
        val connectedTeams: Map<String, Boolean>,
        val currentLot: Int,
        val settings: SettingsRoom,
        val nickname: String,
        val winners: Map<String, String>,
        val bet: String = "",
        val dialogState: Boolean = false
    ) : RoomTeamViewState()
}

@HiltViewModel
class RoomTeamViewModel @Inject constructor(
    private val data: AppData,
    private val roomsRepository: FbRoomsRepository,
    private val usersRepository: FbUsersRepository
): ViewModel(), EventHandler<RoomTeamEvent> {

    private val _roomTeamViewState: MutableLiveData<RoomTeamViewState> =
        MutableLiveData(RoomTeamViewState.Loading)
    val roomTeamViewState: LiveData<RoomTeamViewState> = _roomTeamViewState

    private val mAuth = FirebaseAuth.getInstance()

    override fun obtainEvent(event: RoomTeamEvent) {
        when (val currentState = _roomTeamViewState.value) {
            RoomTeamViewState.WaitStart -> {}
            RoomTeamViewState.Loading -> loadLot()
            is RoomTeamViewState.DisplayLot -> reduce(event, currentState)
            else -> {}
        }
    }

    private fun reduce(event: RoomTeamEvent, currentState: RoomTeamViewState.DisplayLot) {
        when (event) {
            RoomTeamEvent.LoadData -> loadLot()

            is RoomTeamEvent.BetChanged -> {
                if (event.newValue.isDigitsOnly()) {
                    _roomTeamViewState.postValue(
                        currentState.copy(bet = event.newValue)
                    )
                }
            }
            RoomTeamEvent.ChangeDialogState -> {
                _roomTeamViewState.postValue(
                    currentState.copy(dialogState = !currentState.dialogState)
                )
            }
            RoomTeamEvent.MakeBetClicked -> makeBet(currentState, currentState.bet.toInt())
            RoomTeamEvent.AllPayClicked -> makeBet(
                currentState,
                currentState.lots[currentState.currentLot - 1].limitPrice)
            RoomTeamEvent.PassClicked -> makeBet(currentState, 0)
        }
    }

    private fun makeBet(currentState: RoomTeamViewState.DisplayLot, bet: Int) {
        Log.d("currentUid", mAuth.currentUser?.uid ?: "-")
        usersRepository.readNickname(
            "teams",
            onSuccess = {
                roomsRepository.setBet(
                    data.getCode(),
                    currentState.lots[currentState.currentLot - 1].title,
                    it,
                    bet
                )
            },
            onFail = {

            }
        )
        _roomTeamViewState.postValue(
            currentState.copy(
                dialogState = !currentState.dialogState,
            )
        )
    }

    private fun loadLot()  {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val code = data.getCode()
                Log.d("teamLot", code)
                roomsRepository.readRoom(
                    code = code,
                    onSuccess = {
                        _roomTeamViewState.postValue(
                            RoomTeamViewState.DisplayLot(
                                lots = it.lots,
                                connectedTeams = it.connectedTeams,
                                settings = it.setting,
                                currentLot = it.currentLot,
                                nickname = data.getMyNickname(),
                                winners = it.winners
                            )
                        )
                    }
                )

            } catch (e: Exception) {
                Log.e("load", e.toString())
            }
        }

    }

}
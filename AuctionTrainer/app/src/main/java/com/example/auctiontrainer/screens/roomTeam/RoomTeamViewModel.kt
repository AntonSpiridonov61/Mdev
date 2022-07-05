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
import com.example.auctiontrainer.database.firebase.FbHistoryRepository
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
    object LoadData : RoomTeamEvent()
}

sealed class RoomTeamViewState {
    object WaitStart : RoomTeamViewState()
    data class DisplayLot (
        val lot: LotModel = LotModel("-", "-", 0, ""),
        val time: Int = 3,
        val bet: String = "",
        val dialogState: Boolean = false,
        val isMakeBet: Boolean = true
    ) : RoomTeamViewState()
}

@HiltViewModel
class RoomTeamViewModel @Inject constructor(
    private val data: AppData,
    private val roomsRepository: FbRoomsRepository,
    private val usersRepository: FbUsersRepository,
    private val historyRepository: FbHistoryRepository
): ViewModel(), EventHandler<RoomTeamEvent> {

    private val _roomTeamViewState: MutableLiveData<RoomTeamViewState> =
        MutableLiveData(RoomTeamViewState.DisplayLot())
    val roomTeamViewState: LiveData<RoomTeamViewState> = _roomTeamViewState

    private val mAuth = FirebaseAuth.getInstance()

    override fun obtainEvent(event: RoomTeamEvent) {
        when (val currentState = _roomTeamViewState.value) {
            RoomTeamViewState.WaitStart -> {}
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
            RoomTeamEvent.MakeBetClicked -> makeBet(currentState)
        }
    }

    private fun makeBet(currentState: RoomTeamViewState.DisplayLot) {
        Log.d("currentUid", mAuth.currentUser?.uid ?: "-")
        usersRepository.readNickname(
            mAuth.currentUser?.uid ?: "",
            "teams",
            onSuccess = {
                historyRepository.setBet(
                    data.getCode(),
                    currentState.lot.title,
                    it,
                    currentState.bet.toInt()
                )
            },
            onFail = {

            }
        )
        _roomTeamViewState.postValue(
            currentState.copy(
                dialogState = !currentState.dialogState,
                isMakeBet = !currentState.isMakeBet
            )
        )
    }

    private fun loadLot()  {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val code = data.getCode()
                Log.d("teamLot", code)
                roomsRepository.readOneLot(
                    code = code,
                    onSuccess = {
                        _roomTeamViewState.postValue(
                            RoomTeamViewState.DisplayLot(lot = it)
                        )
                    }
                )

            } catch (e: Exception) {
                Log.e("load", e.toString())
            }
        }

    }

}
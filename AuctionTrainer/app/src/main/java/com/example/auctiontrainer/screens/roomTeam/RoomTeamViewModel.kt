package com.example.auctiontrainer.screens.roomTeam

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctiontrainer.base.AppData
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.code
import com.example.auctiontrainer.database.firebase.AppFirebaseRepository
import com.example.auctiontrainer.screens.roomOrganizer.RoomViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RoomTeamEvent {
    data class MakeBet (val bet: String) : RoomTeamEvent()
    object LoadData : RoomTeamEvent()
}

sealed class RoomTeamViewState {
    object WaitStart : RoomTeamViewState()
    data class DisplayLot (
        val lot: LotModel,
        val time: Int,
        val isMakeBet: Boolean
    ) : RoomTeamViewState()
}

@HiltViewModel
class RoomTeamViewModel @Inject constructor(
    private val data: AppData,
    private val firebaseRepository: AppFirebaseRepository
): ViewModel(), EventHandler<RoomTeamEvent> {

    private val _roomTeamViewState: MutableLiveData<RoomTeamViewState> =
        MutableLiveData(RoomTeamViewState.WaitStart)
    val roomTeamViewState: LiveData<RoomTeamViewState> = _roomTeamViewState

    override fun obtainEvent(event: RoomTeamEvent) {
        when (val currentState = _roomTeamViewState.value) {
            RoomTeamViewState.WaitStart -> {}
            is RoomTeamViewState.DisplayLot -> reduce(event, currentState)
        }
    }

    private fun reduce(event: RoomTeamEvent, currentState: RoomTeamViewState) {
        when (event) {
            RoomTeamEvent.LoadData -> loadLot()
        }
    }

    private fun loadLot()  {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val code = data.getCode()
                firebaseRepository.readOneLot(
                    code = com.example.auctiontrainer.base.code,
                    onSuccess = {
                        _roomTeamViewState.postValue(
                            RoomTeamViewState.DisplayLot(it, 3, false)
                        )
                    }
                )

            } catch (e: Exception) {
                Log.e("load", e.toString())
            }
        }

    }

}
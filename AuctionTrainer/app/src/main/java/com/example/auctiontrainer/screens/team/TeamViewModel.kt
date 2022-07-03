package com.example.auctiontrainer.screens.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.database.firebase.AppFirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class TeamEvent {
    data class CodeChanged(val newValue: String) : TeamEvent()
    object ChangeState : TeamEvent()
    object ReadyClicked : TeamEvent()
}

sealed class TeamViewState {
    data class Dialog(val code: String = "") : TeamViewState()
    data class Display(val nickname: String = "Человек") : TeamViewState()
}

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val firebaseRepository: AppFirebaseRepository
): ViewModel(), EventHandler<TeamEvent> {
    private val _teamViewState = MutableLiveData<TeamViewState>(
        TeamViewState.Display()
    )
    val teamViewState: LiveData<TeamViewState> = _teamViewState

    private val mAuth = FirebaseAuth.getInstance()

    override fun obtainEvent(event: TeamEvent) {
        when (val currentViewState = _teamViewState.value) {
            is TeamViewState.Dialog -> reduce(event, currentViewState)
            is TeamViewState.Display -> reduce(event, currentViewState)
            else -> {}
        }
    }

    private fun reduce(event: TeamEvent, currentState: TeamViewState.Dialog) {
        when (event) {
            is TeamEvent.CodeChanged -> _teamViewState.postValue(
                currentState.copy(code = event.newValue)
            )
            is TeamEvent.ChangeState -> changeState(currentState)
            is TeamEvent.ReadyClicked -> readyClick(currentState)
        }
    }

    private fun reduce(event: TeamEvent, currentState: TeamViewState.Display) {

        _teamViewState.postValue(
            currentState.copy(nickname = getNickname())
        )

        when (event) {
            is TeamEvent.ChangeState -> changeState(currentState)
        }
    }

    private fun changeState(state: TeamViewState) {
        when (state) {
            is TeamViewState.Dialog -> {
                _teamViewState.postValue(TeamViewState.Display(getNickname()))
            }
            is TeamViewState.Display -> {
                _teamViewState.postValue(TeamViewState.Dialog())
            }
        }
    }

    private fun readyClick(state: TeamViewState.Dialog) {
        Log.d("code", state.code)
        changeState(state)
    }

    private fun getNickname(): String {
        var nick = "Человек"
        val uid = mAuth.currentUser?.uid
        Log.d("getNick", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq")
        if (uid != null) {
            firebaseRepository.readNickname(
                uid.toString(),
                "teams",
                { nick = it },
                { Log.d("getNick", it) }
            )
        }
        return nick
    }
}


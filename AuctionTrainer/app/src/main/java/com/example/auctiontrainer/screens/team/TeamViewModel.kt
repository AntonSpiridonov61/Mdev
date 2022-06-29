package com.example.auctiontrainer.screens.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auctiontrainer.base.EventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class TeamEvent {
    data class CodeChanged(val newValue: String) : TeamEvent()
    object ChangeState : TeamEvent()
    object ReadyClicked : TeamEvent()
}

sealed class TeamViewState {
    data class ViewStateDialog(val code: String = "") : TeamViewState()
    object ViewStateDisplay : TeamViewState()
}

@HiltViewModel
class TeamViewModel @Inject constructor(): ViewModel(), EventHandler<TeamEvent> {
    private val _teamViewState = MutableLiveData<TeamViewState>(TeamViewState.ViewStateDisplay)
    val teamViewState: LiveData<TeamViewState> = _teamViewState

    override fun obtainEvent(event: TeamEvent) {
        when (val currentViewState = _teamViewState.value) {
            is TeamViewState.ViewStateDialog -> reduce(event, currentViewState)
            is TeamViewState.ViewStateDisplay -> reduce(event, currentViewState)
        }
    }

    private fun reduce(event: TeamEvent, currentState: TeamViewState.ViewStateDialog) {
        when (event) {
            is TeamEvent.CodeChanged -> _teamViewState.postValue(
                currentState.copy(code = event.newValue)
            )
            is TeamEvent.ChangeState -> changeState(currentState)
            is TeamEvent.ReadyClicked -> readyClick(currentState)
        }
    }

    private fun reduce(event: TeamEvent, currentState: TeamViewState.ViewStateDisplay) {
        when (event) {
            is TeamEvent.ChangeState -> changeState(currentState)
        }
    }

    private fun changeState(state: TeamViewState) {
        when (state) {
            is TeamViewState.ViewStateDialog -> {
                _teamViewState.postValue(TeamViewState.ViewStateDisplay)
            }
            TeamViewState.ViewStateDisplay -> {
                _teamViewState.postValue(TeamViewState.ViewStateDialog())
            }
        }
    }

    private fun readyClick(state: TeamViewState.ViewStateDialog) {
        Log.d("code", state.code)
        changeState(state)
    }
}


package com.example.auctiontrainer.screens.team

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctiontrainer.base.AppData
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.database.firebase.FbRoomsRepository
import com.example.auctiontrainer.database.firebase.FbUsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TeamEvent {
    data class CodeChanged(val newValue: String) : TeamEvent()
    object ChangeDialogState : TeamEvent()
    object ReadyClicked : TeamEvent()
    object LoadData : TeamEvent()
}

sealed class TeamViewState {
    data class Display(
        val nickname: String = "Человек",
        val code: String = "",
        val dialogState: Boolean = false
    ) : TeamViewState()
    object Loading : TeamViewState()
    object Success : TeamViewState()
}

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val application: Application,
    private val data: AppData,
    private val roomsRepository: FbRoomsRepository,
    private val usersRepository: FbUsersRepository
): ViewModel(), EventHandler<TeamEvent> {
    private val _teamViewState = MutableLiveData<TeamViewState>(
        TeamViewState.Loading
    )
    val teamViewState: LiveData<TeamViewState> = _teamViewState


    override fun obtainEvent(event: TeamEvent) {
        when (val currentViewState = _teamViewState.value) {
            is TeamViewState.Display -> reduce(event, currentViewState)
            is TeamViewState.Loading -> getNickname()
            else -> {}
        }
    }

    private fun reduce(event: TeamEvent, currentState: TeamViewState.Display) {
        when (event) {
            is TeamEvent.CodeChanged -> _teamViewState.postValue(
                currentState.copy(code = event.newValue)
            )
            is TeamEvent.ChangeDialogState -> changeState(currentState)
            is TeamEvent.ReadyClicked -> readyClick(currentState)
            else -> {}
        }
    }

    private fun changeState(currentState: TeamViewState.Display) {
        _teamViewState.postValue(
            currentState.copy(dialogState = !currentState.dialogState)
        )
    }

    private fun readyClick(currentState: TeamViewState.Display) {
        viewModelScope.launch(Dispatchers.IO) {
            roomsRepository.connectToRoom(
                code = currentState.code,
                onSuccess = {
                    data.setCode(currentState.code)
                    _teamViewState.postValue(TeamViewState.Success)
                },
                onFail = { Toast.makeText(application, it, Toast.LENGTH_LONG).show() }
            )
        }

    }

    private fun getNickname() {
        usersRepository.readNickname(
            "teams",
            {
                data.setNickname(it)
                _teamViewState.postValue(
                    TeamViewState.Display(it)
                )
            },
            { Log.d("getNick", it) }
        )
    }
}


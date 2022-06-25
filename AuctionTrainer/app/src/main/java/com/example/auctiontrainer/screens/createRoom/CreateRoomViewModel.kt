package com.example.auctiontrainer.screens.createRoom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.LotsData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class CreateRoomViewState {
    data class Display(val items: List<LotModel>) : CreateRoomViewState()
    object NoItems: CreateRoomViewState()
    object Loading: CreateRoomViewState()
}

sealed class CreateRoomEvent {
    object EnterScreen : CreateRoomEvent()
    object ReloadScreen : CreateRoomEvent()
}

@HiltViewModel
class CreateRoomViewModel @Inject constructor(): ViewModel(), EventHandler<CreateRoomEvent> {

    private val _createRoomViewState: MutableLiveData<CreateRoomViewState> = MutableLiveData(CreateRoomViewState.NoItems)
    val createRoomViewState: LiveData<CreateRoomViewState> = _createRoomViewState
    private val lotViewModel = LotsData()

    override fun obtainEvent(event: CreateRoomEvent) {
        when (val currentState = _createRoomViewState.value) {
            is CreateRoomViewState.Loading -> reduce(event, currentState)
            is CreateRoomViewState.Display -> reduce(event, currentState)
            is CreateRoomViewState.NoItems -> reduce(event, currentState)
        }
    }

    private fun reduce(event: CreateRoomEvent, currentState: CreateRoomViewState.Loading) {
        when (event) {
            CreateRoomEvent.EnterScreen -> fetchLotForDate()
        }
    }

    private fun reduce(event: CreateRoomEvent, currentState: CreateRoomViewState.NoItems) {
        when (event) {
            CreateRoomEvent.ReloadScreen -> fetchLotForDate(true)
            CreateRoomEvent.EnterScreen -> fetchLotForDate()
        }
    }

    private fun reduce(event: CreateRoomEvent, currentState: CreateRoomViewState.Display) {
        when (event) {
            CreateRoomEvent.EnterScreen -> fetchLotForDate()
        }
    }

    private fun fetchLotForDate(needsToRefresh: Boolean = false) {
        if (needsToRefresh) {
            _createRoomViewState.postValue(CreateRoomViewState.Loading)
        }
        val lots = lotViewModel.data.value
        Log.d("Lot", lots.toString())
        if (lots!!.isEmpty()) {
            Log.d("lots", "isEmpty")
            _createRoomViewState.postValue(CreateRoomViewState.NoItems)
        } else {
            Log.d("lots", "notEmpty")
            _createRoomViewState.postValue(
                CreateRoomViewState.Display(lots)
            )
        }
    }
}
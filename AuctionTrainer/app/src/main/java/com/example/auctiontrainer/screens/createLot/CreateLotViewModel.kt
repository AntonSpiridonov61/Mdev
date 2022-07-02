package com.example.auctiontrainer.screens.createLot

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auctiontrainer.base.AppData
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.base.LotModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class CreateLotEvent {
    data class TitleChanged(val newValue: String) : CreateLotEvent()
    data class PriceChanged(val newValue: Int) : CreateLotEvent()
    data class TypeSelected(val newValue: String) : CreateLotEvent()
    object SaveClicked : CreateLotEvent()
}

sealed class CreateLotViewState {

    data class ViewStateInitial(
        val lotTitle: String = "Name",
        val lotPrice: Int = 0,
        val lotType: String = "Тип 1"
    ) : CreateLotViewState()

    object ViewStateSuccess : CreateLotViewState()
}

@HiltViewModel
class CreateLotViewModel @Inject constructor(
    val data: AppData
): ViewModel(), EventHandler<CreateLotEvent> {

    private val _createLotViewState: MutableLiveData<CreateLotViewState> =
        MutableLiveData(CreateLotViewState.ViewStateInitial())
    val createLotViewState: LiveData<CreateLotViewState> = _createLotViewState

    override fun obtainEvent(event: CreateLotEvent) {
        when (val currentViewState = _createLotViewState.value) {
            is CreateLotViewState.ViewStateInitial -> reduce(event, currentViewState)
            else -> {}
        }
    }

    private fun reduce(event: CreateLotEvent, currentState: CreateLotViewState.ViewStateInitial) {
        when (event) {
            is CreateLotEvent.TitleChanged -> _createLotViewState.postValue(
                currentState.copy(lotTitle = event.newValue)
            )

            is CreateLotEvent.PriceChanged -> _createLotViewState.postValue(
                currentState.copy(lotPrice = event.newValue)
            )

            is CreateLotEvent.TypeSelected -> _createLotViewState.postValue(
                currentState.copy(lotType = event.newValue)
            )

            CreateLotEvent.SaveClicked -> saveLot(currentState)
        }
    }
    private fun saveLot(state: CreateLotViewState.ViewStateInitial) {
        Log.d("Lot", "title: ${state.lotTitle}, type: ${state.lotType}, price: ${state.lotPrice}")
        data.addLots(LotModel(state.lotTitle, state.lotType, state.lotPrice))
        Log.d("LotC", data.getLots().toString())
        _createLotViewState.postValue(CreateLotViewState.ViewStateSuccess)
    }
}
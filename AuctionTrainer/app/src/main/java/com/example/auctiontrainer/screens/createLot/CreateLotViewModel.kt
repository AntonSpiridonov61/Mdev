package com.example.auctiontrainer.screens.createLot

import androidx.core.text.isDigitsOnly
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
    data class StartPriceChanged(val newValue: String) : CreateLotEvent()
    data class LimitPriceChanged(val newValue: String) : CreateLotEvent()
    data class TypeSelected(val newValue: String) : CreateLotEvent()
    object SaveClicked : CreateLotEvent()
}

sealed class CreateLotViewState {

    data class ViewStateInitial(
        val title: String = "",
        val startPrice: String = "",
        val limitPrice: String = "",
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
                currentState.copy(title = event.newValue)
            )

            is CreateLotEvent.StartPriceChanged -> {
                if (event.newValue.isDigitsOnly()) {
                    _createLotViewState.postValue(
                        currentState.copy(startPrice = event.newValue)
                    )
                }
            }

            is CreateLotEvent.LimitPriceChanged -> {
                if (event.newValue.isDigitsOnly()) {
                    _createLotViewState.postValue(
                        currentState.copy(limitPrice = event.newValue)
                    )
                }
            }

            is CreateLotEvent.TypeSelected -> _createLotViewState.postValue(
                currentState.copy(lotType = event.newValue)
            )

            CreateLotEvent.SaveClicked -> saveLot(currentState)
        }
    }

    private fun saveLot(state: CreateLotViewState.ViewStateInitial) {
        data.addLots(LotModel(
            state.title,
            state.lotType,
            state.startPrice.toInt(),
            state.limitPrice.toInt())
        )

        _createLotViewState.postValue(CreateLotViewState.ViewStateSuccess)
    }
}
package com.example.auctiontrainer.screens.organizer


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.database.firebase.AppFirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class OrganizerMainViewState {
    data class Display(val nickname: String = "Человек") : OrganizerMainViewState()
}

sealed class OrganizerMainEvent {
    object CreateClicked : OrganizerMainEvent()
}

@HiltViewModel
class OrganizerMainViewModel @Inject constructor(
    private val firebaseRepository: AppFirebaseRepository
) : ViewModel(), EventHandler<OrganizerMainEvent> {

    private val _organizerMainViewState: MutableLiveData<OrganizerMainViewState> =
        MutableLiveData(OrganizerMainViewState.Display())

    val organizerMainViewState: LiveData<OrganizerMainViewState> = _organizerMainViewState

    private val mAuth = FirebaseAuth.getInstance()

    override fun obtainEvent(event: OrganizerMainEvent) {
        when(val currentViewState = _organizerMainViewState.value) {
            is OrganizerMainViewState.Display -> {
                _organizerMainViewState.postValue(
                    currentViewState.copy(getNickname())
                )
            }
            else -> {}
        }
    }

    private fun getNickname(): String {
        var nick = "Человек"
        val uid = mAuth.currentUser?.uid

        if (uid != null) {
            firebaseRepository.readNickname(
                uid.toString(),
                "organizers",
                { nick = it },
                { Log.d("getNick", it) }
            )
        }
        return nick
    }
}
package com.example.auctiontrainer.screens.organizer


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.database.firebase.FbUsersRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class OrganizerMainViewState {
    data class Display(val nickname: String = "Человек") : OrganizerMainViewState()
    object Loading : OrganizerMainViewState()
}

sealed class OrganizerMainEvent {
    object LoadData : OrganizerMainEvent()
}

@HiltViewModel
class OrganizerMainViewModel @Inject constructor(
    private val usersRepository: FbUsersRepository
) : ViewModel(), EventHandler<OrganizerMainEvent> {

    private val _organizerMainViewState: MutableLiveData<OrganizerMainViewState> =
        MutableLiveData(OrganizerMainViewState.Loading)

    val organizerMainViewState: LiveData<OrganizerMainViewState> = _organizerMainViewState

    private val mAuth = FirebaseAuth.getInstance()

    override fun obtainEvent(event: OrganizerMainEvent) {
        when(val currentViewState = _organizerMainViewState.value) {
            is OrganizerMainViewState.Display -> { }
            is OrganizerMainViewState.Loading -> getNickname()
            else -> {}
        }
    }

    private fun getNickname() {
        val uid = mAuth.currentUser?.uid
        if (uid != null) {
            usersRepository.readNickname(
                "organizers",
                {
                    _organizerMainViewState.postValue(
                        OrganizerMainViewState.Display(it)
                    )
                },
                { Log.d("getNick", it) }
            )
        }
    }
}
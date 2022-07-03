package com.example.auctiontrainer.screens.auth

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctiontrainer.MainActivity
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.database.firebase.AppFirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthViewState {
    data class Registration(
        val nickname: String,
        val email: String,
        val password: String,
        val whatIsRole: String
    ) : AuthViewState()

    data class Login(
        val email: String,
        val password: String
    ) : AuthViewState()

    object Loading: AuthViewState()
    data class Success(val route: String) : AuthViewState()
}

sealed class AuthEvent {
    data class NicknameChanged(val newValue: String) : AuthEvent()
    data class RegEmailChanged(val newValue: String) : AuthEvent()
    data class RegPasswordChanged(val newValue: String) : AuthEvent()
    data class WhatIsRoleChanged(val newValue: String) : AuthEvent()
    data class LoginEmailChanged(val newValue: String) : AuthEvent()
    data class LoginPasswordChanged(val newValue: String) : AuthEvent()
    object MoveReg : AuthEvent()
    object MoveLogin : AuthEvent()
    object Registration : AuthEvent()
    object Login : AuthEvent()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseRepository: AppFirebaseRepository,
    private val application: Application
): ViewModel(), EventHandler<AuthEvent> {

    private val _authViewState: MutableLiveData<AuthViewState> =
        MutableLiveData(AuthViewState.Login("", ""))
    val authViewState: LiveData<AuthViewState> = _authViewState

    override fun obtainEvent(event: AuthEvent) {
        when (val currentState = _authViewState.value) {
            is AuthViewState.Login -> reduce(event, currentState)
            is AuthViewState.Registration -> reduce(event, currentState)
        }
    }

    private fun reduce(event: AuthEvent, currentState: AuthViewState.Login) {
        when (event) {
            is AuthEvent.LoginEmailChanged -> _authViewState.postValue(
                currentState.copy(email = event.newValue)
            )
            is AuthEvent.LoginPasswordChanged -> _authViewState.postValue(
                currentState.copy(password = event.newValue)
            )
            AuthEvent.MoveReg -> {
                _authViewState.postValue(
                    AuthViewState.Registration("", "", "", "Команда")
                )
            }
            AuthEvent.Login -> login(currentState)
        }
    }

        private fun reduce(event: AuthEvent, currentState: AuthViewState.Registration) {
        when (event) {
            is AuthEvent.NicknameChanged -> _authViewState.postValue(
                currentState.copy(nickname = event.newValue)
            )
            is AuthEvent.RegEmailChanged -> _authViewState.postValue(
                currentState.copy(email = event.newValue)
            )
            is AuthEvent.RegPasswordChanged -> _authViewState.postValue(
                currentState.copy(password = event.newValue)
            )
            is AuthEvent.WhatIsRoleChanged -> _authViewState.postValue(
                currentState.copy(whatIsRole = event.newValue)
            )
            AuthEvent.MoveLogin -> {
                _authViewState.postValue(AuthViewState.Login("", ""))
            }
            AuthEvent.Registration -> registration(currentState)
        }
    }

    private fun login(currentState: AuthViewState.Login) {
        Log.d("login", "login")
        firebaseRepository.signIn(
            currentState.email,
            currentState.password,
            { _authViewState.postValue(AuthViewState.Loading) },
            { Toast.makeText(application, it, Toast.LENGTH_LONG).show() }
        )
        val mAuth = FirebaseAuth.getInstance()
        viewModelScope.launch {
            firebaseRepository.whoIsUser(
                mAuth.currentUser?.uid.toString(),
                { _authViewState.postValue(AuthViewState.Success(it)) },
                { Toast.makeText(application, it, Toast.LENGTH_LONG).show() }

            )
        }
    }

    private fun registration(currentState: AuthViewState.Registration) {
        Log.d("reg", "reg")
        firebaseRepository.registration(
            currentState.email,
            currentState.password,
            currentState.nickname,
            currentState.whatIsRole,
            {
                _authViewState.postValue(AuthViewState.Login(currentState.email, currentState.password))
                obtainEvent(AuthEvent.Login)
            },
            { Toast.makeText(application, it, Toast.LENGTH_LONG).show() }
        )

    }
}



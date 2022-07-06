package com.example.auctiontrainer.screens.auth

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctiontrainer.base.EventHandler
import com.example.auctiontrainer.database.firebase.FbUsersRepository
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
    private val usersRepository: FbUsersRepository,
    private val application: Application
): ViewModel(), EventHandler<AuthEvent> {

    private val _authViewState: MutableLiveData<AuthViewState> =
        MutableLiveData(AuthViewState.Login("", ""))
    val authViewState: LiveData<AuthViewState> = _authViewState

    override fun obtainEvent(event: AuthEvent) {
        when (val currentState = _authViewState.value) {
            is AuthViewState.Login -> reduce(event, currentState)
            is AuthViewState.Registration -> reduce(event, currentState)
            else -> { }
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
            else -> { }
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
            else -> { }
        }
    }

    private fun login(currentState: AuthViewState.Login) {
        Log.d("login", "login")
        usersRepository.signIn(
            currentState.email.trim(),
            currentState.password.trim(),
            {
                _authViewState.postValue(AuthViewState.Loading)
                whoIs(it)
            },
            {
                Toast.makeText(application, it, Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun registration(currentState: AuthViewState.Registration) {
        Log.d("reg", "reg")
        usersRepository.registration(
            currentState.email.trim(),
            currentState.password.trim(),
            currentState.nickname.trim(),
            currentState.whatIsRole,
            {
                _authViewState.postValue(AuthViewState.Loading)
                whoIs(it)
            },
            {
                Toast.makeText(application, it, Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun whoIs(uid: String) {
        viewModelScope.launch {
            usersRepository.whoIsUser(
                { _authViewState.postValue(AuthViewState.Success(it)) },
                { Toast.makeText(application, it, Toast.LENGTH_LONG).show() }
            )
        }
    }
}

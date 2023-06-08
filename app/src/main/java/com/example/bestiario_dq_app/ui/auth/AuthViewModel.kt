package com.example.bestiario_dq_app.ui.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestiario_dq_app.data.remote.responses.AuthResult
import com.example.bestiario_dq_app.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        autentificar()
    }

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.onLoginUsernameChanged -> {
                state = state.copy(loginUsername = event.value)
            }

            is AuthUiEvent.onLoginPassChanged -> {
                state = state.copy(loginPass = event.value)
            }

            is AuthUiEvent.Login -> {
                login()
            }

            is AuthUiEvent.onRegistroUsernameChanged -> {
                state = state.copy(registroUsername = event.value)
            }

            is AuthUiEvent.onRegistroPassChanged -> {
                state = state.copy(registroPass = event.value)
            }

            is AuthUiEvent.Registro -> {
                registro()
            }
        }
    }

    private fun registro() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.registro(
                username = state.registroUsername,
                pass = state.registroPass
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.login(
                username = state.loginUsername,
                pass = state.loginPass
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun autentificar() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.autentificar()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}

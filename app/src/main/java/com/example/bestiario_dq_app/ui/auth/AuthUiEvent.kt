package com.example.bestiario_dq_app.ui.auth

sealed class AuthUiEvent {
    data class onRegistroUsernameChanged(val value: String): AuthUiEvent()
    data class onRegistroPassChanged(val value: String): AuthUiEvent()
    object SignUp: AuthUiEvent()

    data class onLoginUsernameChanged(val value: String): AuthUiEvent()
    data class onLoginPassChanged(val value: String): AuthUiEvent()
    object Login: AuthUiEvent()

}
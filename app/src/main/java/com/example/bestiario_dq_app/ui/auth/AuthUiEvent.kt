package com.example.bestiario_dq_app.ui.auth

sealed class AuthUiEvent {
    data class SignUpUsernameChanged(val value: String): AuthUiEvent()
    data class SignUpPasswordChanged(val value: String): AuthUiEvent()
    object SignUp: AuthUiEvent()

    data class SignInUsernameChanged(val value: String): AuthUiEvent()
    data class SignInPasswordChanged(val value: String): AuthUiEvent()
    object SignIn: AuthUiEvent()
}
package com.example.bestiario_dq_app.ui.auth

data class AuthState(
    val isLoading: Boolean = false,
    val registroUsername: String = "",
    val registroPass: String = "",
    val loginUsername: String = "",
    val loginPass: String = "",
    val isRegistroDialogOpen: Boolean = false
)
package com.example.bestiario_dq_app.data.remote.responses

data class TokenResponse(
    val token: String,
    val username: String,
    val admin: Boolean
)
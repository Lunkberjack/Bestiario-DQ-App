package com.example.bestiario_dq_app.domain.repositories

import com.example.bestiario_dq_app.data.remote.responses.AuthResult

interface AuthRepository {
    suspend fun registro(username: String, pass: String): AuthResult<Unit>
    suspend fun login(username: String, pass: String): AuthResult<Unit>
    suspend fun autentificar(): AuthResult<Unit>
    suspend fun prueba()
}
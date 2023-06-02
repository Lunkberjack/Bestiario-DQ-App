package com.example.bestiario_dq_app.domain.repositories

import com.example.bestiario_dq_app.data.remote.responses.AuthResult

interface AuthRepository {
    suspend fun registro(username: String, password: String): AuthResult<Unit>
    suspend fun login(username: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}
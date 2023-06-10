package com.example.bestiario_dq_app.data.repositories

import android.content.SharedPreferences
import com.example.bestiario_dq_app.data.remote.ApiService
import com.example.bestiario_dq_app.data.remote.requests.PeticionAuth
import com.example.bestiario_dq_app.data.remote.responses.AuthResult
import com.example.bestiario_dq_app.domain.repositories.AuthRepository
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val prefs: SharedPreferences
) : AuthRepository {

    override suspend fun registro(username: String, pass: String): AuthResult<Unit> {
        return try {
            apiService.registro(
                request = PeticionAuth(
                    username = username,
                    pass = pass
                )
            )
            // Queremos que el login sea automático cuando el usuario se registra:
            login(username, pass)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.NoAutorizado()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun login(username: String, pass: String): AuthResult<Unit> {
        return try {
            val response = apiService.login(
                request = PeticionAuth(
                    username = username,
                    pass = pass
                )
            )
            prefs.edit()
                .putString("jwt", response.token)
                .apply()
            AuthResult.Autorizado()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.NoAutorizado()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun autentificar(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.NoAutorizado()
            apiService.autentificar("Bearer $token")
            AuthResult.Autorizado()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.NoAutorizado()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun prueba() {
        apiService.prueba()
    }
}
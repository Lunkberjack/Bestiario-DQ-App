package com.example.bestiario_dq_app.data.repositories

import android.content.SharedPreferences
import com.example.bestiario_dq_app.data.remote.AuthApi
import com.example.bestiario_dq_app.data.remote.requests.AuthRequest
import com.example.bestiario_dq_app.data.remote.responses.AuthResult
import com.example.bestiario_dq_app.domain.repositories.AuthRepository
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: SharedPreferences
): AuthRepository {

    override suspend fun registro(username: String, password: String): AuthResult<Unit> {
        return try {
            api.registro(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            // Queremos que el login sea automático cuando el usuario se registra:
            login(username, password)
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun login(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.login(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            prefs.edit()
                .putString("jwt", response.token)
                .apply()
            AuthResult.Authorized()
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }
}
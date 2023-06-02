package com.example.bestiario_dq_app.data.remote

import com.example.bestiario_dq_app.data.remote.requests.AuthRequest
import com.example.bestiario_dq_app.data.remote.responses.TokenResponse

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("registro")
    suspend fun registro(
        @Body request: AuthRequest
    )

    @POST("login")
    suspend fun login(
        @Body request: AuthRequest
    ): TokenResponse

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
}
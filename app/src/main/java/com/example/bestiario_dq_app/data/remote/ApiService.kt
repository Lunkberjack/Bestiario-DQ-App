package com.example.bestiario_dq_app.data.remote

import com.example.bestiario_dq_app.data.remote.requests.PeticionAuth
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.data.remote.responses.TokenResponse

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("registro")
    suspend fun registro(
        @Body request: PeticionAuth
    )

    @POST("login")
    suspend fun login(
        @Body request: PeticionAuth
    ): TokenResponse

    @GET("autentificar")
    suspend fun autentificar(
        @Header("Authorization") token: String
    )

    @GET("prueba")
    suspend fun prueba(  )

    @GET("monstruo-id")
    suspend fun getMonstruoIdLista(idLista: String): Monstruo

    @GET("monstruo-nombre")
    suspend fun getMonstruoNombre(nombre: String): Monstruo

    @GET("monstruos")
    suspend fun getMonstruos(): List<Monstruo>

    @POST("new-monstruo")
    suspend fun newMonstruo(
        @Body request: Monstruo
    )
}
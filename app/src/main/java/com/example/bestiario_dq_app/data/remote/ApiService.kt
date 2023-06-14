package com.example.bestiario_dq_app.data.remote

import com.example.bestiario_dq_app.data.remote.requests.PeticionAuth
import com.example.bestiario_dq_app.data.remote.responses.Familia
import com.example.bestiario_dq_app.data.remote.responses.Juego
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.data.remote.responses.TokenResponse
import kotlinx.coroutines.flow.Flow

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun prueba()

    @GET("monstruo/id/{idLista}")
    suspend fun getMonstruoIdLista(@Path("idLista") idLista: String): Monstruo

    @GET("monstruo/nombre/{nombre}")
    suspend fun getMonstruoNombre(@Path("nombre") nombre: String): Monstruo

    @GET("monstruos")
    suspend fun getMonstruosPaginacion(
        @Query("pagina") page: Int,
        @Query("por_pagina") porPagina: Int
    ): List<Monstruo>

    @GET("monstruos/{order}/{tipo}")
    suspend fun getMonstruos(
        @Path("order") order: String?,
        @Path("tipo") tipo: String?
    ): List<Monstruo>

    @GET("familias")
    suspend fun getFamilias(): List<Familia>

    @GET("juegos")
    suspend fun getJuegos(): List<Juego>

    @POST("new-monstruo")
    suspend fun newMonstruo(
        @Body request: Monstruo
    )

    @GET("familia/{nombre}")
    suspend fun getFamilia(
        @Path("nombre")
        nombre: String
    ): Familia

    @GET("juego/{abr}")
    suspend fun getJuego(
        @Path("abr")
        abr: String
    ): Juego

    @GET("monstruos/familia/{familia}")
    suspend fun filtroFamilia(
        @Path("familia")
        familia: String
    ): List<Monstruo>

    @GET("monstruos/juego/{juego}")
    suspend fun filtroJuego(
        @Path("juego")
        juego: String
    ): List<Monstruo>
}
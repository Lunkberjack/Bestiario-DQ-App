package com.example.bestiario_dq_app.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.example.bestiario_dq_app.data.remote.ApiService
import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.example.bestiario_dq_app.data.remote.responses.Familia
import com.example.bestiario_dq_app.data.remote.responses.Juego
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import javax.inject.Inject

class MonstruosRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefs: SharedPreferences
) : MonstruosRepository {
    override suspend fun newMonstruo(
        idLista: String,
        nombre: String,
        imagen: String,
        familia: String,
        atributos: List<Atributo>
    ) {
        try {
            apiService.newMonstruo(
                request = Monstruo(
                    idLista = idLista,
                    nombre = nombre,
                    // TODO - Convertir a base64
                    imagen = imagen,
                    familia = familia,
                    atributos = atributos
                )
            )
        } catch (e: Exception) {
            Log.d("asdf", "OH NO NO SE AÑADIÓ TU MONSTRUO")
        }
    }

    override suspend fun getMonstruoIdLista(idLista: String): Monstruo {
        return apiService.getMonstruoIdLista(idLista)
    }

    override suspend fun getMonstruoNombre(nombre: String): Monstruo {
        return apiService.getMonstruoNombre(nombre)
    }

    override suspend fun getMonstruos(): List<Monstruo> {
        return apiService.getMonstruos()
    }

    override suspend fun getFamilias(): List<Familia> {
        return apiService.getFamilias()
    }

    override suspend fun getJuegos(): List<Juego> {
        return apiService.getJuegos()
    }

    override suspend fun getFamilia(nombre: String): Familia {
        return apiService.getFamilia(nombre)
    }

    override suspend fun getJuego(abr: String): Juego {
        return apiService.getJuego(abr)
    }

    override suspend fun filtrarFamilia(familia: String): List<Monstruo> {
        return apiService.filtroFamilia(familia)
    }

    override suspend fun filtrarJuego(juego: String): List<Monstruo> {
        return apiService.filtroJuego(juego)
    }
}
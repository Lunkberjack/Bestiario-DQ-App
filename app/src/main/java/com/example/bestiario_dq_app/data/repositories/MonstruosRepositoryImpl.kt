package com.example.bestiario_dq_app.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.example.bestiario_dq_app.data.remote.ApiService
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import javax.inject.Inject

class MonstruosRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefs: SharedPreferences
): MonstruosRepository {
    override suspend fun newMonstruo(idLista: String, nombre: String, imagen: String) {
        try {
            apiService.newMonstruo(
                request = Monstruo(
                    idLista = idLista,
                    nombre = nombre,
                    imagen = imagen
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
}
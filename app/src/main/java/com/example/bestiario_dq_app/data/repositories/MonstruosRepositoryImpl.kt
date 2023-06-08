package com.example.bestiario_dq_app.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.example.bestiario_dq_app.data.remote.Api
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import javax.inject.Inject

class MonstruosRepositoryImpl @Inject constructor(
    private val api: Api,
    private val prefs: SharedPreferences
): MonstruosRepository {
    override suspend fun newMonstruo(nombre: String, imagen: String) {
        try {
            api.newMonstruo(
                request = Monstruo(
                    nombre = nombre,
                    imagen = imagen
                )
            )
        } catch (e: Exception) {
            Log.d("asdf", "OH NO NO SE AÑADIÓ TU MONSTRUO")
        }
    }

    override suspend fun getMonstruos(): List<Monstruo> {
        return api.getMonstruos()
    }
}
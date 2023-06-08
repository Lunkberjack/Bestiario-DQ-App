package com.example.bestiario_dq_app.domain.repositories

import com.example.bestiario_dq_app.data.remote.responses.Monstruo

interface MonstruosRepository {
    suspend fun newMonstruo(nombre: String, imagen: String)
    suspend fun getMonstruos(): List<Monstruo>
}
package com.example.bestiario_dq_app.domain.repositories

import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.example.bestiario_dq_app.data.remote.responses.Monstruo

interface MonstruosRepository {
    suspend fun newMonstruo(idLista: String, nombre: String, imagen: String, familia: String, atributos: List<Atributo>)
    suspend fun getMonstruoIdLista(idLista: String): Monstruo
    suspend fun getMonstruoNombre(nombre: String): Monstruo
    suspend fun getMonstruos(): List<Monstruo>
}
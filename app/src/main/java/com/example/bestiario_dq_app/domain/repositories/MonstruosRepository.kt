package com.example.bestiario_dq_app.domain.repositories

import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.example.bestiario_dq_app.data.remote.responses.Familia
import com.example.bestiario_dq_app.data.remote.responses.Juego
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import kotlinx.coroutines.flow.Flow

interface MonstruosRepository {
    suspend fun newMonstruo(monstruo: Monstruo)
    suspend fun actualizarMonstruo(idLista: String, monstruo: Monstruo)
    suspend fun borrarMonstruo(idLista: String)
    suspend fun getMonstruoIdLista(idLista: String): Monstruo
    suspend fun getMonstruoNombre(nombre: String): Monstruo
    suspend fun getMonstruos(orden: String?, tipo: String?): Flow<List<Monstruo>>
    suspend fun getFamilias(): Flow<List<Familia>>
    suspend fun getJuegos(): Flow<List<Juego>>
    suspend fun getFamilia(nombre: String): Familia
    suspend fun getJuego(abr: String): Juego
    suspend fun filtrarFamilia(familia: String): Flow<List<Monstruo>>
    suspend fun filtrarJuego(juego: String): Flow<List<Monstruo>>
}
package com.example.bestiario_dq_app.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.example.bestiario_dq_app.data.remote.ApiService
import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.example.bestiario_dq_app.data.remote.responses.Familia
import com.example.bestiario_dq_app.data.remote.responses.Juego
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.data.remote.responses.MonstruoBusqueda
import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MonstruosRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefs: SharedPreferences
) : MonstruosRepository {

    override suspend fun newMonstruo(monstruo: Monstruo) {
        return apiService.newMonstruo(monstruo)
    }

    override suspend fun actualizarMonstruo(idLista: String, monstruo: Monstruo) {
        return apiService.actualizarMonstruo(monstruo.idLista, monstruo)
    }


    override suspend fun borrarMonstruo(idLista: String) {
        return apiService.borrarMonstruo(idLista)
    }

    override suspend fun getMonstruoIdLista(idLista: String): Monstruo {
        return apiService.getMonstruoIdLista(idLista)
    }

    override suspend fun getMonstruoNombre(nombre: String): Monstruo {
        return apiService.getMonstruoNombre(nombre)
    }

    override suspend fun getMonstruos(orden: String?, tipo: String?): Flow<List<Monstruo>> {
        return flow {
            val monstruos = apiService.getMonstruos(orden, tipo)
            emit(monstruos)
        }
    }

    override suspend fun getMonstruosBusqueda(orden: String?, tipo: String?): Flow<List<MonstruoBusqueda>> {
        return flow {
            val monstruosBusqueda = apiService.getMonstruosBusqueda(orden, tipo)
            emit(monstruosBusqueda)
        }
    }

    override suspend fun getFamilias(): Flow<List<Familia>> {
        return flow {
            val familias = apiService.getFamilias()
            emit(familias)
        }
    }

    override suspend fun getJuegos(): Flow<List<Juego>> {
        return flow {
            val juegos = apiService.getJuegos()
            emit(juegos)
        }
    }

    override suspend fun getFamilia(nombre: String): Familia {
        return apiService.getFamilia(nombre)
    }

    override suspend fun getJuego(abr: String): Juego {
        return apiService.getJuego(abr)
    }

    override suspend fun filtrarFamilia(familia: String): Flow<List<Monstruo>> {
        return flow {
            val filtro = apiService.filtroFamilia(familia)
            emit(filtro)
        }
    }

    override suspend fun filtrarJuego(juegoP: String): Flow<List<Monstruo>> {
        val juego = getJuego(juegoP)
        // Buscamos por abreviatura
        return flow {
            val filtro = apiService.filtroJuego(juego.abr)
            emit(filtro)
        }
    }
}
package com.example.bestiario_dq_app.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.bestiario_dq_app.data.local.MonstruoDatabase
import com.example.bestiario_dq_app.data.local.MonstruoEntity
import com.example.bestiario_dq_app.data.mappers.toMonstruoEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MonstruoRemoteMediator(
    private val monstruoDatabase: MonstruoDatabase,
    private val apiService: ApiService
): RemoteMediator<Int, MonstruoEntity>() {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MonstruoEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val ultimo = state.lastItemOrNull()
                    if(ultimo == null) {
                        1
                    } else {
                        // TODO - Puede que haya un error por este casting mi compa, cuidao
                        (ultimo.idLista.toInt() / state.config.pageSize) + 1
                    }
                }
            }

            val monstruos = apiService.getMonstruosPaginacion(
                page = loadKey,
                porPagina = state.config.pageSize
            )

            // Una transacción permite que, si alguna parte del bloque falla, se deshagan
            // todos los cambios realizados. O todo se ejecuta o nada.
            monstruoDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    monstruoDatabase.dao.clearAll()
                }
                val monstruoEntities = monstruos.map { it.toMonstruoEntity() }
                monstruoDatabase.dao.upsertAll(monstruoEntities)
            }

            // Mientras haya elementos, se devolverán. En el momento en que se devuelva una
            // lista vacía, se entenderá que es el fin de la paginación.
            MediatorResult.Success(
                endOfPaginationReached = monstruos.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
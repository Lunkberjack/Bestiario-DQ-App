package com.example.bestiario_dq_app.data.remote
/*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.bestiario_dq_app.data.local.MonstruoDatabase
import com.example.bestiario_dq_app.data.local.MonstruoEntity
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
                        (ultimo.idLista / state.config.pageSize) + 1
                    }
                }
            }

            val monstruos = apiService.getMonstruos()

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
*/
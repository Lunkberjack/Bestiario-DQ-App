package com.example.bestiario_dq_app.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.bestiario_dq_app.data.mappers.toMonstruoEntity
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import kotlinx.coroutines.flow.Flow

@Dao
interface MonstruoDao {
    @Upsert
    suspend fun upsertAll(monstruos: List<MonstruoEntity>)

    // Distinguimos por número de páginas (int) y cada página contiene MonstruoEntities.
    @Query("SELECT * FROM monstruoentity ORDER BY idLista ASC")
    fun getMonstruosDao(): List<MonstruoEntity>

    @Query("SELECT * FROM monstruoentity ORDER BY nombre ASC")
    fun getMonstruosDaoOrdenAlfabetico(): List<MonstruoEntity>

    @Query("DELETE FROM monstruoentity")
    suspend fun clearAll()

    @Upsert
    suspend fun insertMonstruo(monstruo: MonstruoEntity)

    @Delete
    suspend fun deleteMonstruo(monstruo: MonstruoEntity)

    @Query("SELECT COUNT(*) FROM monstruoentity WHERE idLista = :idLista")
    suspend fun isFavorito(idLista: String): Boolean
}
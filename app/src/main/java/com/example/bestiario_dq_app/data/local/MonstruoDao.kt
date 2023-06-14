package com.example.bestiario_dq_app.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
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
    @Query("SELECT * FROM monstruoentity")
    fun getMonstruosDao(): List<MonstruoEntity>

    @Query("DELETE FROM monstruoentity")
    suspend fun clearAll()

    @Upsert
    suspend fun insertMonstruo(monstruo: MonstruoEntity)
}
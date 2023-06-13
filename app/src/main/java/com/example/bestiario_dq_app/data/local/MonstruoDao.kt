package com.example.bestiario_dq_app.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MonstruoDao {
    @Upsert
    suspend fun upsertAll(monstruos: List<MonstruoEntity>)

    // Distinguimos por número de páginas (int) y cada página contiene MonstruoEntities.
    @Query("SELECT * FROM monstruoentity")
    fun pagingSource(): PagingSource<Int, MonstruoEntity>

    @Query("DELETE FROM monstruoentity")
    suspend fun clearAll()
}
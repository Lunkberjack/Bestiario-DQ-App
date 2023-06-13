package com.example.bestiario_dq_app.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MonstruoDao {
    @Upsert
    suspend fun upsertAll(monstruos: List<MonstruoEntity>)

    @Query("DELETE FROM monstruoentity")
    suspend fun clearAll()
}
package com.example.bestiario_dq_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MonstruoEntity::class, AtributoEntity::class],
    version = 1
)
abstract class MonstruoDatabase: RoomDatabase() {
    abstract val dao: MonstruoDao
}
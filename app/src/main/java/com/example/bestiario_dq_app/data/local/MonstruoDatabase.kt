package com.example.bestiario_dq_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bestiario_dq_app.data.mappers.Converters

@Database(
    entities = [MonstruoEntity::class, AtributoEntity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class MonstruoDatabase: RoomDatabase() {
    abstract val dao: MonstruoDao
}
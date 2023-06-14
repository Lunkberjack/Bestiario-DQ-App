package com.example.bestiario_dq_app.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["experiencia", "juego", "oro"])

data class AtributoEntity(
    val experiencia: Int,
    val juego: String,
    val lugares: List<String>,
    val objetos: List<String>,
    val oro: Int
)
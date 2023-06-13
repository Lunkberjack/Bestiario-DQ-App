package com.example.bestiario_dq_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bestiario_dq_app.data.remote.responses.Atributo

@Entity
data class MonstruoEntity(
    val atributos: List<Atributo>,
    val familia: String,
    @PrimaryKey(autoGenerate = false)
    val idLista: String,
    val imagen: String,
    val nombre: String
)
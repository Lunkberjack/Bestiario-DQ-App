package com.example.bestiario_dq_app.data.remote.responses

data class Atributo(
    val experiencia: Int,
    val juego: String,
    val lugares: List<String>,
    val objetos: List<String>,
    val oro: Int
)
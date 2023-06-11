package com.example.bestiario_dq_app.data.remote.responses

data class Monstruo(
    val atributos: List<Atributo>,
    val familia: String,
    val idLista: String,
    val imagen: String,
    val nombre: String
)
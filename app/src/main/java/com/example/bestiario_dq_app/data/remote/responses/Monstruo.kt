package com.example.bestiario_dq_app.data.remote.responses

data class Monstruo(
    var atributos: MutableList<Atributo>,
    var familia: String,
    var idLista: String,
    var imagen: String,
    var nombre: String
)
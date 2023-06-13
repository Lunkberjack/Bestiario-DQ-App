package com.example.bestiario_dq_app.data.mappers

import com.example.bestiario_dq_app.data.local.MonstruoEntity
import com.example.bestiario_dq_app.data.remote.responses.Monstruo

/**
 * Conversión entre documentos JSON y entidades de Room.
 */
fun Monstruo.toMonstruoEntity(): MonstruoEntity {
    return MonstruoEntity(
        idLista = idLista,
        nombre = nombre,
        familia = familia,
        imagen = imagen,
        atributos = atributos
    )
}

fun MonstruoEntity.toMonstruo(): Monstruo {
    return Monstruo(
        idLista = idLista,
        nombre = nombre,
        familia = familia,
        imagen = imagen,
        atributos = atributos
    )
}
package com.example.bestiario_dq_app.data.mappers

import com.example.bestiario_dq_app.data.local.AtributoEntity
import com.example.bestiario_dq_app.data.local.MonstruoEntity
import com.example.bestiario_dq_app.data.remote.responses.Atributo
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

fun Atributo.toAtributoEntity(): AtributoEntity {
    return AtributoEntity(
        experiencia = experiencia,
        juego = juego,
        lugares = lugares,
        objetos = objetos,
        oro = oro
    )
}

fun AtributoEntity.toAtributo(): Atributo {
    return Atributo(
        experiencia = experiencia,
        juego = juego,
        lugares = lugares,
        objetos = objetos,
        oro = oro
    )
}
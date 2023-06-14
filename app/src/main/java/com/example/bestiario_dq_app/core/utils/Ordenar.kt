package com.example.bestiario_dq_app.core.utils

/**
 * Lógica para ordenar los Monstruos en la lista.
 */
sealed class Ordenar(val tipoOrden: TipoOrden) {
    class Numero(tipoOrden: TipoOrden): Ordenar(tipoOrden)
    class Nombre(tipoOrden: TipoOrden): Ordenar(tipoOrden)

    fun copiar(tipoOrden: TipoOrden): Ordenar {
        return when(this) {
            is Numero -> Numero(tipoOrden)
            is Nombre -> Nombre(tipoOrden)
        }
    }
}
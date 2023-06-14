package com.example.bestiario_dq_app.core.utils

/**
 * Tipo de ordenación que se aplicará a la lista.
 */
sealed class TipoOrden {
    object Ascendente: TipoOrden()
    object Descendente: TipoOrden()
}

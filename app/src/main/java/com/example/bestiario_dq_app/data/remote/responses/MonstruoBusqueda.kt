package com.example.bestiario_dq_app.data.remote.responses

/**
 * Optimización: la búsqueda no necesita la imagen, que es demasiado grande para cargar tan seguido.
 */
data class MonstruoBusqueda(
    var idLista: String,
    var nombre: String
) {
    fun coincideBusqueda(query: String): Boolean {
        return nombre.startsWith(query, ignoreCase = true)
    }
}
package com.example.bestiario_dq_app.ui

/**
 * Clase envoltorio para las diferentes rutas (pantallas)
 * a las que puede acceder el NavHost.
 */
sealed class Screen(val route: String) {
    object Auth: Screen(route = "auth")
    object Secret: Screen(route = "secret")
    object Monstruos: Screen(route = "monstruos")
    object Perfil: Screen(route = "perfil")
    object Favoritos: Screen(route = "favoritos")
    object Detalle: Screen(route = "detalle")
}
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
    object DetalleRoom: Screen(route = "detalleroom")
    object Settings: Screen(route = "settings")
    object Familia: Screen(route = "familia")
    object Juego: Screen(route = "juego")
    object Orden: Screen(route = "orden")
    object Admin: Screen(route = "admin")
    object AniadirMonstruo: Screen(route = "aniadirMonstruo")
}

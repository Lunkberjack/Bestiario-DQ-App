package com.example.bestiario_dq_app.ui.auth

sealed class Screen(val route: String) {
    object Auth: Screen(route = "auth_screen")
    object Detail: Screen(route = "detail_screen")
}

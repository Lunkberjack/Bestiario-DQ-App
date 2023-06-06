package com.example.bestiario_dq_app.ui.auth

sealed class Screen(val route: String) {
    object Auth: Screen(route = "auth")
    object Secret: Screen(route = "secret")
}

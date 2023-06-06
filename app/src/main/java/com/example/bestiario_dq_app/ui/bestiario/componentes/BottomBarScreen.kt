package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Monstruos: BottomBarScreen(
        route = "monstruos",
        title = "Monstruos",
        icon = Icons.Default.Home
    )

    object Perfil: BottomBarScreen(
        route = "perfil",
        title = "Perfil",
        icon = Icons.Default.Person
    )

    object Favoritos: BottomBarScreen(
        route = "favoritos",
        title = "Favoritos",
        icon = Icons.Default.Star
    )
}
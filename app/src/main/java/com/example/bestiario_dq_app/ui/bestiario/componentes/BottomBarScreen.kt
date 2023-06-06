package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bestiario_dq_app.ui.auth.Screen

sealed class BottomBarScreen(
    val destination: Screen,
    val title: String,
    val icon: ImageVector
) {
    object Monstruos: BottomBarScreen(
        destination = Screen.Monstruos,
        title = "Monstruos",
        icon = Icons.Default.Home
    )

    object Perfil: BottomBarScreen(
        destination = Screen.Perfil,
        title = "Perfil",
        icon = Icons.Default.Person
    )

    object Favoritos: BottomBarScreen(
        destination = Screen.Favoritos,
        title = "Favoritos",
        icon = Icons.Default.Star
    )
}
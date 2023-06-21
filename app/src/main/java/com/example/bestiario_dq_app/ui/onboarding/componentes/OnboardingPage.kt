package com.example.bestiario_dq_app.ui.onboarding.componentes

import com.example.bestiario_dq_app.R
import androidx.annotation.DrawableRes

/**
 * Clase envoltorio para las distintas páginas del Onboarding.
 */
sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object Primera : OnBoardingPage(
        image = R.drawable.limo,
        title = "Bienvenid@ :)",
        description = "Bienvenido al Bestiario de Dragon Quest.\nPuedes navegar con la barra inferior (Home, Favoritos y Perfil).",
    )

    object Segunda : OnBoardingPage(
        image = R.drawable.dientecillos,
        title = "Funciones",
        description = "En el menú lateral puedes filtrar y ordenar, y en la barra superior tienes una completa búsqueda."
    )

    object Tercera : OnBoardingPage(
        // Cambiar por cosas con sentido.
        image = R.drawable.dracanino,
        title = "Ayuda interactiva",
        description = "Si tienes alguna duda, puedes repetir este tutorial en el menú opciones."
    )
}
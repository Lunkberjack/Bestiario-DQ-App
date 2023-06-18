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
        title = "Meeting",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )

    object Segunda : OnBoardingPage(
        image = R.drawable.dientecillos,
        title = "Coordination",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )

    object Tercera : OnBoardingPage(
        // Cambiar por cosas con sentido.
        image = R.drawable.dracanino,
        title = "Dialogue",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod."
    )
}
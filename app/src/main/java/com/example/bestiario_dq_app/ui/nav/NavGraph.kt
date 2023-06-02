package com.example.bestiario_dq_app.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bestiario_dq_app.ui.auth.AuthScreen
import com.example.bestiario_dq_app.ui.auth.Screen
import com.example.bestiario_dq_app.ui.bestiario.HomeScreen
import dagger.hilt.android.HiltAndroidApp

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Auth.route) {
        composable(
            route = Screen.Auth.route
        ) {
            HomeScreen()
        }

        composable(
            route = Screen.Detail.route
        ) {

        }
    }
}
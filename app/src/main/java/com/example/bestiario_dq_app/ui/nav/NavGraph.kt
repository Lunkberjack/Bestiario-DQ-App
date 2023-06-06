package com.example.bestiario_dq_app.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bestiario_dq_app.ui.auth.AuthScreen
import com.example.bestiario_dq_app.ui.auth.Screen
import com.example.bestiario_dq_app.ui.auth.SecretScreen
import com.example.bestiario_dq_app.ui.bestiario.FavoritosScreen
import com.example.bestiario_dq_app.ui.bestiario.MonstruosScreen
import com.example.bestiario_dq_app.ui.bestiario.PerfilScreen
import com.example.bestiario_dq_app.ui.bestiario.componentes.BottomBarScreen
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Auth.route) {
        composable(route = Screen.Auth.route) {
            AuthScreen(navController = navController)
        }
        composable(route = Screen.Secret.route) {
            SecretScreen()
        }
        composable(route = Screen.Monstruos.route) {
            MonstruosScreen(navHostController = navController)
        }
        composable(route = Screen.Favoritos.route) {
            FavoritosScreen(navHostController = navController)
        }
        composable(route = Screen.Perfil.route) {
            PerfilScreen(navHostController = navController)
        }
    }
}

package com.example.bestiario_dq_app.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bestiario_dq_app.ui.auth.AuthScreen
import com.example.bestiario_dq_app.ui.auth.Screen
import com.example.bestiario_dq_app.ui.auth.SecretScreen
import com.example.bestiario_dq_app.ui.bestiario.FavoritosScreen
import com.example.bestiario_dq_app.ui.bestiario.MonstruosScreen
import com.example.bestiario_dq_app.ui.bestiario.PerfilScreen
import com.example.bestiario_dq_app.ui.bestiario.componentes.BottomBar
import com.example.bestiario_dq_app.ui.bestiario.componentes.BottomBarScreen
import com.example.bestiario_dq_app.ui.bestiario.componentes.DefaultAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val screens = listOf(
        BottomBarScreen.Monstruos,
        BottomBarScreen.Favoritos,
        BottomBarScreen.Perfil
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { DefaultAppBar(scrollBehavior) },
        bottomBar = {
            BottomBar(navController = navController, screens = screens, currentDestination = currentDestination)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = Screen.Monstruos.route) {
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
    }
}

package com.example.bestiario_dq_app.ui.nav

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.bestiario_dq_app.core.utils.hayInternet
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.data.local.MonstruoDao_Impl
import com.example.bestiario_dq_app.ui.auth.AuthScreen
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.auth.SecretScreen
import com.example.bestiario_dq_app.ui.bestiario.DetalleScreen
import com.example.bestiario_dq_app.ui.bestiario.FamiliaScreen
import com.example.bestiario_dq_app.ui.bestiario.FavoritosScreen
import com.example.bestiario_dq_app.ui.bestiario.JuegoScreen
import com.example.bestiario_dq_app.ui.bestiario.MonstruosEvent
import com.example.bestiario_dq_app.ui.bestiario.MonstruosScreen
import com.example.bestiario_dq_app.ui.bestiario.OrdenadaScreen
import com.example.bestiario_dq_app.ui.bestiario.PerfilScreen
import com.example.bestiario_dq_app.ui.bestiario.SettingsScreen
import com.example.bestiario_dq_app.ui.bestiario.componentes.AppDrawer
import com.example.bestiario_dq_app.ui.bestiario.componentes.BottomBar
import com.example.bestiario_dq_app.ui.bestiario.componentes.BottomBarScreen
import com.example.bestiario_dq_app.ui.bestiario.componentes.DefaultAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navController: NavHostController, monstruoDao: MonstruoDao) {
    if (!hayInternet(LocalContext.current)) {
        navController.navigate(Screen.Favoritos.route)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val screens = listOf(
        BottomBarScreen.Monstruos,
        BottomBarScreen.Favoritos,
        BottomBarScreen.Perfil
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = { AppDrawer(Screen.Monstruos.route, navController) },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                // Como tenemos una ruta con parámetros, tenemos que comprobar que no pertenezca a una
                // pantalla de detalle (que se muestra en pantalla completa).
                if ((navBackStackEntry?.destination?.route)?.contains("detalle") == false) {
                    DefaultAppBar(scrollBehavior, drawerState)
                }
            },
            bottomBar = {
                // Lo mismo con la barra de navegación.
                if ((navBackStackEntry?.destination?.route)?.contains("detalle") == false) {
                    BottomBar(
                        navController = navController,
                        screens = screens,
                        currentDestination = currentDestination
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(navController = navController, startDestination = Screen.Auth.route) {
                    composable(route = Screen.Auth.route) {
                        AuthScreen(navController = navController)
                    }
                    composable(route = Screen.Secret.route) {
                        SecretScreen()
                    }
                    composable(route = Screen.Monstruos.route) {
                        MonstruosScreen(navController, monstruoDao = monstruoDao)
                    }
                    composable(route = Screen.Favoritos.route) {
                        FavoritosScreen(navController, monstruoDao = monstruoDao)
                    }
                    composable(route = Screen.Perfil.route) {
                        PerfilScreen(navController)
                    }
                    composable(route = Screen.Settings.route) {
                        SettingsScreen()
                    }
                    // En este caso, pasamos el id de cada monstruo como parámetro para navegar a la carta
                    // de detalles.
                    composable(
                        route = "${Screen.Detalle.route}/{idSeleccionado}",
                        arguments = listOf(navArgument("idSeleccionado") {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        val idSeleccionado = backStackEntry.arguments?.getString("idSeleccionado")
                        DetalleScreen(
                            navController = navController,
                            idSeleccionado = idSeleccionado
                        )
                    }
                    composable(
                        route = "${Screen.Familia.route}/{nombre}",
                        arguments = listOf(navArgument("nombre") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val nombre = backStackEntry.arguments?.getString("nombre")
                        FamiliaScreen(
                            familia = nombre,
                            navController = navController,
                            monstruoDao = monstruoDao
                        )
                    }
                    composable(
                        route = "${Screen.Juego.route}/{abr}",
                        arguments = listOf(navArgument("abr") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val abr = backStackEntry.arguments?.getString("abr")
                        JuegoScreen(
                            abr = abr,
                            navController = navController,
                            monstruoDao = monstruoDao
                        )
                    }
                    composable(
                        route = "${Screen.Orden.route}/{orden}/{tipo}",
                        arguments = listOf(
                            navArgument("orden") { type = NavType.StringType },
                            navArgument("tipo") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val orden = backStackEntry.arguments?.getString("orden")
                        val tipo = backStackEntry.arguments?.getString("tipo")
                        if (orden != null && tipo != null) {
                            OrdenadaScreen(
                                orden = orden,
                                tipo = tipo,
                                navController = navController,
                                monstruoDao = monstruoDao
                            )
                        }
                    }
                }
            }
        }
    }
}
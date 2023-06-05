package com.example.bestiario_dq_app.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bestiario_dq_app.ui.bestiario.componentes.BottomBarScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Monstruos.route
    ) {
        composable(route = BottomBarScreen.Monstruos.route) {
            ScreenContent(
                name = BottomBarScreen.Monstruos.route,
                onClick = {
                    navController.navigate(Graph.DETALLE)
                }
            )
        }
        composable(route = BottomBarScreen.Perfil.route) {
            ScreenContent(
                name = BottomBarScreen.Perfil.route,
                onClick = { }
            )
        }
        composable(route = BottomBarScreen.Favoritos.route) {
            ScreenContent(
                name = BottomBarScreen.Favoritos.route,
                onClick = { }
            )
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETALLE,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }
        }
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object Overview : DetailsScreen(route = "OVERVIEW")
}
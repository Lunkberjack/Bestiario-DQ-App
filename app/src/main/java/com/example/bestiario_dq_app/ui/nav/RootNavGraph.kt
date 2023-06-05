package com.example.bestiario_dq_app.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bestiario_dq_app.ui.bestiario.HomeScreen

@Composable
fun RootNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController, route = Graph.ROOT,
        startDestination = Graph.AUTH
    ) {
        // todo authnavgraph
        composable(route = Graph.HOME) {
            HomeScreen()
        }
    }
}

object Graph {
    const val AUTH = "auth"
    const val HOME = "home"
    const val ROOT = "root"
    const val DETALLE = "detalle"
}
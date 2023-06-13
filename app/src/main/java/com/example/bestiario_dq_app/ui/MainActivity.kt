package com.example.bestiario_dq_app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bestiario_dq_app.core.utils.hayInternet
import com.example.bestiario_dq_app.ui.auth.AuthViewModel
import com.example.bestiario_dq_app.ui.nav.NavGraph
import com.example.bestiario_dq_app.ui.theme.BestiarioDQAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BestiarioDQAppTheme {
                if(!hayInternet(LocalContext.current)) {
                    navController.navigate(Screen.Favoritos.route)
                }

                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
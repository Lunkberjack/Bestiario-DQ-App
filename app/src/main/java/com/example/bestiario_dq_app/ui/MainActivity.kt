package com.example.bestiario_dq_app.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bestiario_dq_app.core.utils.hayInternet
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.ui.auth.AuthViewModel
import com.example.bestiario_dq_app.ui.nav.NavGraph
import com.example.bestiario_dq_app.ui.theme.BestiarioDQAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var monstruoDao: MonstruoDao
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BestiarioDQAppTheme {
                navController = rememberNavController()
                // El contexto es para que las SharedPrefs sean compartidas en toda la aplicación.
                NavGraph(navController = navController, monstruoDao = monstruoDao, context = LocalContext.current)
            }
        }
    }
}
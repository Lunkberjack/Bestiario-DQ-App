package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bestiario_dq_app.ui.bestiario.componentes.BottomBar
import com.example.bestiario_dq_app.ui.bestiario.componentes.BottomBarScreen
import com.example.bestiario_dq_app.ui.bestiario.componentes.DefaultAppBar

@Composable
fun FavoritosScreen(navHostController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Pantalla favoritos", fontSize = 10.sp)
    }
}
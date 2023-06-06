package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bestiario_dq_app.ui.bestiario.componentes.DefaultAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(navHostController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // EL innerPadding permite que la barra superior no se superponga al contenido.
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { DefaultAppBar(scrollBehavior) },
        bottomBar = { BottomBar(navController = navHostController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Pantalla favoritos", fontSize = 10.sp)
        }
    }
}


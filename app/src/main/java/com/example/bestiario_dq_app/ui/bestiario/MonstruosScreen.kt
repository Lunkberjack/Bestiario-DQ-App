package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bestiario_dq_app.domain.repositories.AuthRepository
import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import com.example.bestiario_dq_app.ui.auth.AuthUiEvent
import com.example.bestiario_dq_app.ui.auth.AuthViewModel
import com.example.bestiario_dq_app.ui.bestiario.componentes.BottomBar
import com.example.bestiario_dq_app.ui.bestiario.componentes.BottomBarScreen

// https://dragon-quest.org/wiki/Monsters#Families

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonstruosScreen(
    navController: NavController,
    viewModel: BestiarioViewModel = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row() {
            Button(
                onClick = {
                    viewModel.onEvent(BestiarioEvent.onTraerMonstruos)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Traer monstruos")
            }
        }
    }

}

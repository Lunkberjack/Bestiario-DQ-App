package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bestiario_dq_app.ui.bestiario.componentes.CartaMonstruo
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.ui.theme.manrope

@Composable
fun OrdenadaScreen(
    orden: String,
    tipo: String,
    navController: NavController,
    viewModel: MonstruosViewModel = hiltViewModel(),
    monstruoDao: MonstruoDao
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ordenado por $orden tipo $tipo",
            fontFamily = manrope,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
        val monstruosFlow = viewModel.monstruos.collectAsState()
        LaunchedEffect(Unit) {
            viewModel.getMonstruos(orden = orden, tipo = tipo)
        }
        val monstruos = monstruosFlow.value

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(monstruos) { monstruo ->
                    CartaMonstruo(navController, monstruo, monstruoDao)
                }
            }
        }
    }
}


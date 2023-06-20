package com.example.bestiario_dq_app.ui.bestiario


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bestiario_dq_app.ui.bestiario.componentes.CartaMonstruo
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.ui.theme.manrope

// https://dragon-quest.org/wiki/Monsters#Families

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuegoScreen(
    abr: String?,
    navController: NavController,
    monstruoDao: MonstruoDao,
    viewModel: MonstruosViewModel = hiltViewModel()
) {
    val monstruos by viewModel.monstruos.collectAsState(emptyList())
    if (abr != null) {
        viewModel.filtrarJuego(abr)
    }

    // Si la lista aún no se ha llenado, mostramos una pantalla de carga.
    if (monstruos.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Cargando juego...", fontFamily = manrope, fontWeight = FontWeight.Light)
        }
        return
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            buildAnnotatedString {
                append("Juego\n")
                withStyle(
                    style = SpanStyle(
                        fontFamily = manrope,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                ) {
                    append(abr)
                }
            },
            fontFamily = manrope,
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        LazyColumn {
            items(monstruos) { monstruo ->
                CartaMonstruo(navController, monstruo, monstruoDao)
            }
        }
    }
}

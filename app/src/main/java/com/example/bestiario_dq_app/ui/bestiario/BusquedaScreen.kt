package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bestiario_dq_app.ui.bestiario.componentes.ResultadoBusqueda
import com.example.bestiario_dq_app.ui.theme.manrope

@Composable
fun BusquedaScreen(navController: NavController, viewModel: MonstruosViewModel, searchViewModel: SearchViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)) {
        Row(modifier = Modifier
            .weight(0.33f)
            .fillMaxWidth()) {
            Column {
                ApartadoBusqueda(apartado = "Monstruos")
                LazyColumn {
                    items(viewModel.monstruos.value) { monstruo ->
                        ResultadoBusqueda(
                            navController = navController,
                            titulo = monstruo.nombre,
                            subtitulo = monstruo.familia,
                            id = monstruo.idLista,
                            searchViewModel = searchViewModel
                        )
                    }
                }
            }
        }
        Row(modifier = Modifier
            .weight(0.33f)
            .fillMaxWidth()) {
            Column {
                ApartadoBusqueda(apartado = "Familias")
                LazyColumn {
                    items(viewModel.familias.value) { familia ->
                        ResultadoBusqueda(
                            navController = navController,
                            titulo = familia.nombre,
                            subtitulo = "Ir a familia",
                            searchViewModel = searchViewModel
                        )
                    }
                }
            }
        }
        Row(modifier = Modifier
            .weight(0.33f)
            .fillMaxWidth()) {
            Column {
                ApartadoBusqueda(apartado = "Juegos")
                LazyColumn {
                    items(viewModel.juegos.value) { juego ->
                        ResultadoBusqueda(
                            navController = navController,
                            titulo = juego.nombre,
                            subtitulo = "Ir a juego",
                            abr = juego.abr,
                            searchViewModel = searchViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ApartadoBusqueda(apartado: String) {
    Text(text = apartado, fontSize = 22.sp, fontFamily = manrope, fontWeight = FontWeight.ExtraBold)
}
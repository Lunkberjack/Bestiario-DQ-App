package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.bestiario.SearchViewModel
import com.example.bestiario_dq_app.ui.theme.manrope

@Composable
fun ResultadoBusqueda(
    navController: NavController,
    titulo: String,
    subtitulo: String,
    // Para poder abrir la carta de Monstruo o Juego.
    id: String? = null,
    abr: String? = null,
    searchViewModel: SearchViewModel
) {
    Divider()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable {
                searchViewModel.setSearchActive(false)
                if (id != null)
                    navController.navigate(Screen.Detalle.route + "/${id}")
                if (abr != null)
                    navController.navigate(Screen.Juego.route + "/${abr}")
                if (abr == null && id == null)
                    navController.navigate(Screen.Familia.route + "/${titulo}")
            }) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = titulo,
                fontSize = 14.sp,
                fontFamily = manrope,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = subtitulo,
                fontSize = 10.sp,
                fontFamily = manrope,
                fontWeight = FontWeight.Light
            )
        }
    }
}
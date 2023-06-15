package com.example.bestiario_dq_app.ui.bestiario.componentes

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bestiario_dq_app.ui.theme.manrope

@Composable
fun ResultadoBusqueda(
    titulo: String,
    subtitulo: String
) {
    Divider()
    Column(Modifier.fillMaxWidth().padding(6.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Text(text = titulo, fontSize = 12.sp, fontFamily = manrope, fontWeight = FontWeight.ExtraBold)
        }
        Row(Modifier.fillMaxWidth()) {
            Text(text = subtitulo, fontSize = 8.sp, fontFamily = manrope, fontWeight = FontWeight.Light)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultadoBusqueda(){
    Column{
        ResultadoBusqueda("hola", "si")
        ResultadoBusqueda("hola", "no")
        ResultadoBusqueda("hola", "no")
        ResultadoBusqueda("hola", "no")
    }
}
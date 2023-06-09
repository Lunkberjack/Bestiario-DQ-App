package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.utils.base64ToBitmap

/**
 * Cada representación de una entidad de Monstruo en la base de datos.
 * TODO - Hacer que los colores se generen desde la imagen con Palette API.
 */
@Composable
fun CartaMonstruo(monstruo: Monstruo) {
    Box(modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .border(width = 2.dp, color = Color.DarkGray, shape = RoundedCornerShape(20.dp))
        ) {
            Column(Modifier.weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                // Si la imagen no es nula:
                base64ToBitmap(monstruo.imagen)?.let {
                    Image(
                        bitmap = it.asImageBitmap(), // Convertimos el Bitmap a ImageBitmap.
                        contentDescription = "Carta monstruo",
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                    )
                }
            }
            Column(
                Modifier
                    .weight(0.5f)
                    .padding(end = 20.dp), horizontalAlignment = Alignment.End
            ) {
                Text(text = monstruo.nombre, fontSize = 30.sp)
                //Text(text = "#${monstruo.id}")
                Text(text = "#000")
                Spacer(Modifier.height(20.dp))
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Agregar a favoritos (descargar)"
                )
            }
        }
    }
}
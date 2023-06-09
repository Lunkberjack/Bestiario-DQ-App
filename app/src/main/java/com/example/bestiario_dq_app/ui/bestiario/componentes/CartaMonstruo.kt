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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.ui.theme.manrope
import com.example.bestiario_dq_app.utils.base64ToBitmap

/**
 * Cada representación de una entidad de Monstruo en la base de datos.
 * TODO - Hacer que los colores se generen desde la imagen con Palette API.
 */
@Composable
fun CartaMonstruo(monstruo: Monstruo) {
    val bitmapPaleta = base64ToBitmap(monstruo.imagen, 200, 200)
    val paletaMonstruo = bitmapPaleta?.let { Palette.from(it).generate() }

    Box(modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)) {
        if (paletaMonstruo != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(20.dp))
                    // Si por alguna razón Palette API falla al generarla.
                    .background(Color(paletaMonstruo.getLightVibrantColor(Color.LightGray.toArgb())))
                    //.border(width = 2.dp, color = Color(paletaMonstruo.getVibrantColor(0x00000000)), shape = RoundedCornerShape(20.dp))
            ) {
                Column(Modifier.weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                    // Si la imagen no es nula:
                    Image(
                        bitmap = bitmapPaleta.asImageBitmap(), // Convertimos el Bitmap a ImageBitmap.
                        contentDescription = "Carta monstruo",
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                    )
                }
                Column(
                    Modifier
                        .weight(0.5f)
                        .padding(end = 20.dp), horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = monstruo.nombre,
                        fontSize = 28.sp,
                        fontFamily = manrope,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(paletaMonstruo.getDarkVibrantColor(0xFF000000.toInt()))
                    )
                    //Text(text = "#${monstruo.id}")
                    Text(text = "#000", fontFamily = manrope, fontWeight = FontWeight.Light)
                    Spacer(Modifier.height(20.dp))
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Agregar a favoritos (descargar)",
                        tint = Color(paletaMonstruo.getDarkVibrantColor(0xFF000000.toInt()))
                    )
                }
            }
        }
    }
}
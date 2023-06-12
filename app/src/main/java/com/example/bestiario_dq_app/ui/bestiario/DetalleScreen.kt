package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import com.example.bestiario_dq_app.ui.theme.manrope
import com.example.bestiario_dq_app.core.utils.base64ToBitmap

@Composable
fun DetalleScreen(
    idSeleccionado: String?,
    viewModel: MonstruosViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Encontramos el monstruo en la base de datos con el id que nos hemos traído de la
        // pantalla anterior como parámetro de navegación.
        LaunchedEffect(idSeleccionado) {
            idSeleccionado?.let { viewModel.getMonstruoId(it) }
        }

        val monstruoState by viewModel.monstruo.collectAsState(null)
        val bitmapPaleta = monstruoState?.let { base64ToBitmap(it.imagen, 200, 200) }
        val paletaMonstruo = bitmapPaleta?.let { Palette.from(it).generate() }

        if (paletaMonstruo != null) {
            // Diferentes fallbacks por si ese color en específico no se ha generado. Debería ser suficiente con dos fallbacks.
            EllipticalShape(
                Color(
                    paletaMonstruo.getLightVibrantColor(
                        Color(
                            paletaMonstruo.getVibrantColor(
                                Color(paletaMonstruo.getDominantColor(Color.LightGray.toArgb())).toArgb()
                            )
                        ).toArgb()
                    )
                )
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 140dp es la altura del óvalo.
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
                    .height(100.dp), horizontalArrangement = Arrangement.Start
            ) {
                monstruoState?.let {
                    if (paletaMonstruo != null) {
                        Text(
                            text = it.nombre,
                            fontFamily = manrope,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 40.sp,
                            color = Color(
                                paletaMonstruo.getDarkVibrantColor(
                                    Color(
                                        paletaMonstruo.getVibrantColor(
                                            Color(paletaMonstruo.getLightVibrantColor(Color.LightGray.toArgb())).toArgb()
                                        )
                                    ).toArgb()
                                )
                            )
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
                    .height(100.dp), horizontalArrangement = Arrangement.Start
            ) {
                monstruoState?.let {
                    if (paletaMonstruo != null) {
                        Text(
                            text = "#${it.idLista}",
                            fontFamily = manrope,
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp,
                            color = Color(
                                paletaMonstruo.getDarkVibrantColor(
                                    Color(
                                        paletaMonstruo.getVibrantColor(
                                            Color(paletaMonstruo.getLightVibrantColor(Color.LightGray.toArgb())).toArgb()
                                        )
                                    ).toArgb()
                                )
                            )
                        )
                    }
                }
            }

            Row {
                if (bitmapPaleta != null) {
                    Image(
                        bitmap = bitmapPaleta.asImageBitmap(),
                        contentDescription = "Detalles monstruo",
                        modifier = Modifier
                            .width(250.dp)
                            .height(250.dp)
                    )
                }
            }
            Column {
                monstruoState?.let {
                    Text(text = it.familia)
                    for (each in monstruoState?.atributos!!) {
                        Text(
                            text = "Juego: ${each.juego}",
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = manrope
                        )

                        Text(text = "Experiencia: ${each.experiencia}")
                        Text(text = "Oro: ${each.oro}")


                        for (lugar in each.lugares) {
                            Text(text = "Lugar: $lugar")

                        }
                        for (objeto in each.objetos) {
                            Text(text = "Objeto: $objeto")
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun EllipticalShape(color: Color) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        drawOval(
            color = color,
            size = Size(width = 1000.dp.toPx(), height = 540.dp.toPx()),
            topLeft = Offset(x = -450.dp.toPx(), y = -400.dp.toPx())
        )
    }
}
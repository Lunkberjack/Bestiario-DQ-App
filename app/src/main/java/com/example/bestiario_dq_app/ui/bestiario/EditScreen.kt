package com.example.bestiario_dq_app.ui.bestiario

import com.example.bestiario_dq_app.data.remote.responses.Monstruo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import com.example.bestiario_dq_app.ui.theme.manrope
import com.example.bestiario_dq_app.core.utils.base64ToBitmap
import com.example.bestiario_dq_app.ui.bestiario.componentes.JuegoExpansible

@Composable
fun EditScreen(
    navController: NavController,
    monstruo: Monstruo
) {
    var idListaState by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        val bitmapPaleta = monstruo?.let { base64ToBitmap(it.imagen, 200, 200) }
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
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp), horizontalArrangement = Arrangement.Start
            ) {
                monstruo?.let {
                    if (paletaMonstruo != null) {
                        Column {
                            TextField(
                                value = it.idLista,
                                onValueChange = {
                                    idListaState = it
                                }
                            )

                            Text(
                                text = it.nombre,
                                style = TextStyle(
                                    lineHeight = 35.sp // Establece el interlineado a 24sp
                                ),
                                fontFamily = manrope,
                                fontWeight = FontWeight.ExtraBold,
                                // Si el nombre es demasiado largo se reduce la letra.
                                fontSize = if(it.nombre.length > 10) 40.sp else 50.sp,
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
            // Familia, atributos por juego
            Column(modifier = Modifier.width(300.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                monstruo?.let {
                    if (paletaMonstruo != null) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontFamily = manrope,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 20.sp,
                                        color = Color(paletaMonstruo.getLightVibrantColor(Color.LightGray.toArgb()))
                                    )
                                ) {
                                    append("Familia: ")
                                }
                                append(it.familia)
                            },
                            fontFamily = manrope,
                            fontWeight = FontWeight.Light,
                            fontSize = 18.sp,
                        )
                    }
                    for (each in monstruo.atributos) {
                        if (paletaMonstruo != null) {
                            JuegoExpansible(atributo = each, paletaMonstruo = paletaMonstruo)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}
package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.example.bestiario_dq_app.ui.theme.manrope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuegoExpansible(
    paletaMonstruo: Palette,
    atributo: Atributo
) {
    var expandidoState by remember { mutableStateOf(false) }
    val giroState by animateFloatAsState(
        // La flecha de expandir y cerrar debe girar con los clicks.
        targetValue = if (expandidoState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .width(200.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        onClick = {
            expandidoState = !expandidoState
        },
        colors = CardDefaults.cardColors(
            // Copiamos el color para poder modificar su transparencia.
            containerColor = Color(
                paletaMonstruo.getDarkVibrantColor(
                    Color(
                        paletaMonstruo.getVibrantColor(
                            Color(paletaMonstruo.getLightVibrantColor(Color.LightGray.toArgb())).toArgb()
                        )
                    ).toArgb()
                )
            ).copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = manrope,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                color = Color(
                                    paletaMonstruo.getDarkVibrantColor(
                                        Color(
                                            paletaMonstruo.getVibrantColor(
                                                Color(paletaMonstruo.getLightVibrantColor(Color.LightGray.toArgb())).toArgb()
                                            )
                                        ).toArgb()
                                    )
                                ),
                            )
                        ) {
                            append("Juego: ")
                        }
                        append(atributo.juego)
                    },
                    fontFamily = manrope,
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                IconButton(
                    modifier = Modifier
                        .offset(x = 20.dp)
                        .rotate(giroState),
                    onClick = {
                        expandidoState = !expandidoState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Flechita"
                    )
                }
            }
            if (expandidoState) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = manrope,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp,
                            )
                        ) {
                            append("Experiencia: ")
                        }
                        append(atributo.experiencia.toString())
                    },
                    fontFamily = manrope,
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                )

                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = manrope,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp,
                            )
                        ) {
                            append("Oro: ")
                        }
                        append(atributo.oro.toString())
                    },
                    fontFamily = manrope,
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                )


                Text(
                    text = "Lugares:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontFamily = manrope
                )

                for (lugar in atributo.lugares) {
                    Text(
                        text = "- $lugar",
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        fontFamily = manrope
                    )
                }
                Text(
                    text = "Objetos:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontFamily = manrope
                )
                for (objeto in atributo.objetos) {
                    Text(
                        text = "- $objeto",
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        fontFamily = manrope
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

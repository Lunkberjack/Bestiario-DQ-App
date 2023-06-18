package com.example.bestiario_dq_app.ui.bestiario

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import androidx.preference.PreferenceManager
import com.example.bestiario_dq_app.ui.theme.manrope
import com.example.bestiario_dq_app.core.utils.base64ToBitmap
import com.example.bestiario_dq_app.core.utils.hayInternet
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.data.mappers.toMonstruoEntity
import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.bestiario.componentes.JuegoExpansible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DetalleScreen(
    navController: NavController,
    idSeleccionado: String?,
    monstruoDao: MonstruoDao,
    context: Context,
    viewModel: MonstruosViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val isAdmin = prefs.getBoolean("admin", false)

    if (isAdmin) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            if (idSeleccionado != null) {
                                viewModel.borrarMonstruo(idSeleccionado)
                            }
                            val monstruo = idSeleccionado?.let { monstruoDao.getMonstruoId(it) }
                            if (monstruo != null) {
                                monstruoDao.deleteMonstruo(monstruo)
                            }
                        }
                    }
                },
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar monstruo",
                    Modifier.size(20.dp)
                )
            }

            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.EditarMonstruo.route + "/${viewModel.monstruo.value?.idLista}")
                },
                modifier = Modifier
                    .padding(20.dp)
                    .offset(y = (-50).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar monstruo",
                    Modifier.size(20.dp)
                )
            }
        }
    }


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
                    .padding(start = 20.dp), horizontalArrangement = Arrangement.Start
            ) {
                monstruoState?.let {
                    if (paletaMonstruo != null) {
                        Box {
                            Text(
                                text = "#${it.idLista}",
                                fontFamily = manrope,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 50.sp,
                                color = Color(
                                    paletaMonstruo.getLightVibrantColor(
                                        Color(
                                            paletaMonstruo.getVibrantColor(
                                                Color(paletaMonstruo.getDominantColor(Color.LightGray.toArgb())).toArgb()
                                            )
                                        ).toArgb()
                                    )
                                ),
                                modifier = Modifier
                                    .offset(y = 525.dp, x = (-95).dp)
                                    .rotate(-90f)
                            )

                            Text(
                                text = it.nombre,
                                style = TextStyle(
                                    lineHeight = 35.sp // Establece el interlineado a 24sp
                                ),
                                fontFamily = manrope,
                                fontWeight = FontWeight.ExtraBold,
                                // Si el nombre es demasiado largo se reduce la letra.
                                fontSize = if (it.nombre.length > 10) 40.sp else 50.sp,
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
            Column(
                modifier = Modifier.width(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                monstruoState?.let {
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
                    for (each in monstruoState?.atributos!!) {
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


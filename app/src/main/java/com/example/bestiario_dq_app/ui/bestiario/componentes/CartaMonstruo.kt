package com.example.bestiario_dq_app.ui.bestiario.componentes

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.palette.graphics.Palette
import androidx.preference.PreferenceManager
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.bestiario.MonstruosViewModel
import com.example.bestiario_dq_app.ui.theme.manrope
import com.example.bestiario_dq_app.core.utils.base64ToBitmap
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.data.mappers.toMonstruoEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Cada representación de una entidad de Monstruo en la base de datos.
 * TODO - Hacer que los colores se generen desde la imagen con Palette API.
 */
@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun CartaMonstruo(
    navController: NavController,
    monstruo: Monstruo,
    monstruoDao: MonstruoDao
) {
    val coroutineScope = rememberCoroutineScope()
    val bitmapPaleta = base64ToBitmap(monstruo.imagen, 200, 200)
    val paletaMonstruo = bitmapPaleta?.let { Palette.from(it).generate() }

    Box(
        modifier = Modifier
            .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
            .clickable {
                // Si venimos de la Screen favoritos, cogemos los detalles de Room. Además poppeamos la
                // backstack sólo hasta Favoritos, no hasta Monstruos (lo otro significaría que, cada vez
                // que cerramos la carta de un monstruo favorito, cambiemos de pantalla y no no).
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                if (currentRoute == Screen.Favoritos.route) {
                    navController.navigate(Screen.DetalleRoom.route + "/${monstruo.idLista}")
                } else {
                    navController.navigate(Screen.Detalle.route + "/${monstruo.idLista}")
                }
            }
    ) {
        if (paletaMonstruo != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
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
            ) {
                Column(Modifier.weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        bitmap = bitmapPaleta.asImageBitmap(),
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
                        textAlign = TextAlign.End,
                        // Creo que no deberíamos pasarnos de 2 líneas (pero nunca se sabe, es Dragon Quest T_T).
                        maxLines = 2,
                        // Si el nombre es demasiado largo se reduce la letra.
                        fontSize = if (monstruo.nombre.length > 10) 17.5.sp else 28.sp,
                        fontFamily = manrope,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(
                            paletaMonstruo.getDarkVibrantColor(
                                Color.White.toArgb()
                            )
                        )
                    )
                    Text(
                        text = "#${monstruo.idLista}",
                        fontFamily = manrope,
                        fontWeight = FontWeight.Light,
                        color = Color(
                            paletaMonstruo.getDarkVibrantColor(
                                Color.White.toArgb()
                            )
                        )
                    )
                    Spacer(Modifier.height(20.dp))

                    val _isFavoritoFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
                    val icono = remember { mutableStateOf(Icons.Outlined.Add) }

                    //val currentBackStackEntry by navController.currentBackStackEntryAsState()
                    //val currentRoute = currentBackStackEntry?.destination?.route

                    IconButton(onClick = {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                val favorito = monstruoDao.isFavorito(monstruo.idLista)
                                if (favorito) {
                                    monstruoDao.deleteMonstruo(monstruo.toMonstruoEntity())

                                    withContext(Dispatchers.Main) {
                                        // Cómo lo explico. Para evitar que un usuario traviesillo quiera entrar a los detalles
                                        // de un monstruo que ya se ha borrado de Room, actualizamos la página en tiempo real.
                                        // Al no tener la posibilidad de usar Flows, lo hacemos un poco rudimentario pero que
                                        // funciona 👍
                                        val currentRoute =
                                            navController.currentBackStackEntry?.destination?.route
                                        if (currentRoute == Screen.Favoritos.route) {
                                            navController.navigate(Screen.Favoritos.route) {
                                                navController.popBackStack(
                                                    Screen.Monstruos.route,
                                                    inclusive = false,
                                                    saveState = false
                                                )
                                            }
                                        }
                                    }
                                    icono.value = Icons.Outlined.Add
                                } else {
                                    monstruoDao.insertMonstruo(monstruo.toMonstruoEntity())
                                    withContext(Dispatchers.Main) {
                                        icono.value = Icons.Outlined.Star
                                    }
                                }
                                withContext(Dispatchers.Main) {
                                    _isFavoritoFlow.value = !favorito
                                }
                            }
                        }
                    }) {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                val favorito = monstruoDao.isFavorito(monstruo.idLista)
                                if (favorito) {
                                    icono.value = Icons.Outlined.Star
                                } else {
                                    icono.value = Icons.Outlined.Add
                                }
                                _isFavoritoFlow.value = !favorito
                            }
                        }
                        Icon(
                            imageVector = icono.value,
                            contentDescription = "Agregar a favoritos (descargar)",
                            tint = Color(
                                paletaMonstruo.getDarkVibrantColor(
                                    Color.White.toArgb()
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

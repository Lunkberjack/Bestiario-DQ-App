package com.example.bestiario_dq_app.ui.bestiario

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.ui.bestiario.componentes.CartaMonstruo
import com.example.bestiario_dq_app.core.utils.Globals
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

// https://dragon-quest.org/wiki/Monsters#Families

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonstruosScreen(
    navController: NavController,
    viewModel: MonstruosViewModel = hiltViewModel()
) {
    /* TODO - Borrar esto. Es una prueba para desarrollar la interfaz sin conexión a la API
    val monstruos = listOf(
        Monstruo(
            nombre = "Limo",
            imagen = ""
        ),
        Monstruo(
            nombre = "Lima",
            imagen = ""
        ),
        Monstruo(nombre = "Dracanino", imagen = "")
    )
     */

    // Guardamos la lista de monstruos que el ViewModel muestra como State (mutable).
    val monstruos = viewModel.monstruos
    // Y actualizamos su estado.
    viewModel.onEvent(MonstruosEvent.onTraerMonstruos)

    // Ahora podemos acceder a todos los datos de los monstruos y mostrarlos en
    // nuestro View. Como estamos suscritos al estado del ViewModel, cualquier
    // cambio que ocurra en éste se reflejará también en la LazyColumn.
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(monstruos) { monstruo ->
                CartaMonstruo(navController, monstruo)
            }
        }
        if(Globals.esAdmin) {
            Text(text = "ADMIIIIIIIIIIIIIIIIIIIIIIIIIIIIIINNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN", fontSize = 40.sp)
        }
    }
}
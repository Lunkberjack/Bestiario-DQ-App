package com.example.bestiario_dq_app.ui.bestiario

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

// https://dragon-quest.org/wiki/Monsters#Families

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonstruosScreen(
    navController: NavController,
    viewModel: MonstruosViewModel = hiltViewModel()) {

    // Guardamos la lista de monstruos que el ViewModel muestra como State (mutable).
    val monstruos = viewModel.monstruos
    // Y actualizamos su estado.
    viewModel.onEvent(MonstruosEvent.onTraerMonstruos)

    // Ahora podemos acceder a todos los datos de los monstruos y mostrarlos en
    // nuestro View. Como estamos suscritos al estado del ViewModel, cualquier
    // cambio que ocurra en éste se reflejará también en la LazyColumn.
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(monstruos) {monstruo ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = monstruo.nombre, fontSize = 30.sp)
                    // Si no es nula:
                    base64ToBitmap(monstruo.imagen)?.let { Image(bitmap = it.asImageBitmap(), contentDescription = "Carta monstruo", modifier = Modifier.width(100.dp).height(100.dp)) }
                }
            }
        }
    }
}

fun base64ToBitmap(base64: String): Bitmap? {
    val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}
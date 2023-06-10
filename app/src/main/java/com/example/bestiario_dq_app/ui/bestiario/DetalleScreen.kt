package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.utils.base64ToBitmap

@Composable
fun DetalleScreen(
    monstruo: Monstruo?,
    navController: NavController,
    viewModel: MonstruosViewModel = hiltViewModel()
) {
    val bitmapPaleta = monstruo?.let { base64ToBitmap(it.imagen, 200, 200) }

    Box(modifier = Modifier) {
        Column() {
            Row() {
                if (monstruo != null) {
                    Text(text = monstruo.nombre)
                    Text(text = monstruo.idLista)
                }
            }
            Row {
                if (bitmapPaleta != null) {
                    Image(
                        bitmap = bitmapPaleta.asImageBitmap(), // Convertimos el Bitmap a ImageBitmap.
                        contentDescription = "Carta monstruo",
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                    )
                }
            }
            Row() {
                Text(text = "Aquí deberían de estar los detalles")
            }
        }
    }
}
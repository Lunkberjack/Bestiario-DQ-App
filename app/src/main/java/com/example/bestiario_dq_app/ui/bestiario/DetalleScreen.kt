package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bestiario_dq_app.utils.base64ToBitmap

@Composable
fun DetalleScreen(
    idSeleccionado: String?,
    viewModel: MonstruosViewModel = hiltViewModel()
) {
    // Encontramos el monstruo en la base de datos con el id que nos hemos traído de la
    // pantalla anterior como parámetro de navegación.
    LaunchedEffect(idSeleccionado) {
        idSeleccionado?.let { viewModel.getMonstruoId(it) }
    }

    val monstruoState by viewModel.monstruo.collectAsState(null)

    val bitmapPaleta = monstruoState?.let { base64ToBitmap(it.imagen, 200, 200) }

    Box(modifier = Modifier) {
        Column {
            Row {
                monstruoState?.let { Text(text = it.nombre) }
                monstruoState?.let { Text(text = it.idLista) }
            }
            Row {
                if (bitmapPaleta != null) {
                    Image(
                        bitmap = bitmapPaleta.asImageBitmap(),
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

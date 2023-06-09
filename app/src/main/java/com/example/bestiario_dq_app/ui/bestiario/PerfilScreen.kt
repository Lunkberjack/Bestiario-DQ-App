package com.example.bestiario_dq_app.ui.bestiario

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bestiario_dq_app.R

// https://www.artstation.com/artwork/R3vDgA

@Composable
fun PerfilScreen(navHostController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedImage: ImageBitmap? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Primero convierte el recurso a Bitmap usando su id, y después convierte ese Bitmap a ImageBitmap.
        val default: ImageBitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.limo).asImageBitmap()
        Image(
            bitmap = selectedImage ?: default,
            contentDescription = "Profilbild",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .clickable { showDialog = true }
        )

        // Nombre de usuario
        Text(text = "Username", fontWeight = FontWeight.Black, fontSize = 30.sp)

        // Descripción, estado, acciones, etc
        Text(text = "Description, todo", fontWeight = FontWeight.Light, fontSize = 18.sp)
    }

    // AlertDialog para cambiar el avatar
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Elige tu avatar") },
            // No mostramos un texto sino nuestro selector de avatar
            text = {
                ImageSelector(
                    onImageSelected = { image ->
                        selectedImage = image
                        showDialog = false
                    }
                )
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}

@Composable
fun ImageSelector(onImageSelected: (ImageBitmap) -> Unit) {
    val images = listOf(
        R.drawable.dientecillos,
        R.drawable.limo,
        R.drawable.dracanino,
        R.drawable.punkitorrinco,
        R.drawable.almaignea,
        R.drawable.atlas,
        R.drawable.chumbo,
        R.drawable.golem,
        R.drawable.macero,
        R.drawable.sanguinino,
        R.drawable.seta
    )
    // Las transformamos en Bitmap.
    val imagesBitmap = mutableListOf<Bitmap>()
    for(image in images) {
        imagesBitmap.add(BitmapFactory.decodeResource(LocalContext.current.resources, image))
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(imagesBitmap) { image ->
            val bitmap = image.asImageBitmap()
           // val bitmap: ImageBitmap = BitmapFactory.decodeResource(LocalContext.current.resources, imageRes).asImageBitmap()
            Image(
                bitmap = bitmap,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 10.dp)
                    .safeContentPadding()
                    .clickable { onImageSelected(bitmap) }
            )
        }
    }
}
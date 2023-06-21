package com.example.bestiario_dq_app.ui.bestiario

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.preference.PreferenceManager
import com.example.bestiario_dq_app.R
import com.example.bestiario_dq_app.core.utils.imagenPreferencias
import com.example.bestiario_dq_app.core.utils.setImagenPreferencias
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.theme.manrope

// https://www.artstation.com/artwork/R3vDgA

@Composable
fun PerfilScreen(context: Context, navController: NavController) {
    // Cada bitmap lleva su R.drawable.id "atado" mediante un mapa. Podemos recuperar el valor
    // de R.drawable.id para guardarlo en SharedPreferences y que el avatar del usuario persista.
    val bitmapMap: Map<Int, ImageBitmap> = mapOf(
        R.drawable.dientecillos to BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.dientecillos
        ).asImageBitmap(),
        R.drawable.limo to BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.limo
        ).asImageBitmap(),
        R.drawable.dracanino to BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.dracanino
        ).asImageBitmap(),
        R.drawable.punkitorrinco to BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.punkitorrinco
        ).asImageBitmap(),
        R.drawable.almaignea to BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.almaignea
        ).asImageBitmap(),
        R.drawable.atlas to BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.atlas
        ).asImageBitmap(),
        R.drawable.chumbo to BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.chumbo
        ).asImageBitmap(),
        R.drawable.sanguinino to BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.sanguinino
        ).asImageBitmap(),
        R.drawable.seta to BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.seta
        ).asImageBitmap()
    )

    var showDialog by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    // No tiene ningún sentido poner otra vez R.drawable.limo (ya estamos seguros de que
    // será el default por imagenPreferencias()) pero es un poco más legible que poner 0.
    var selectedImageId by remember { mutableStateOf<Int?>(R.drawable.limo) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Traemos el avatar que el usuario ha guardado anteriormente como SharedPreference.
        // Si no ha guardado ninguno aún, tiene un precioso limo como perfil (impl. de imagenPreferencias).
        val avatarPreferencias = imagenPreferencias(context)

        // Primero convierte el recurso a Bitmap usando su id, y después convierte ese Bitmap a ImageBitmap.
        val avatar: ImageBitmap =
            BitmapFactory.decodeResource(LocalContext.current.resources, avatarPreferencias)
                .asImageBitmap()
        val isAdmin =
            PreferenceManager.getDefaultSharedPreferences(context).getBoolean("admin", false)
        if(isAdmin) {
            Image(
                painter = painterResource(id = R.drawable.corona),
                contentDescription = "Corona de admin",
                modifier = Modifier
                    .size(150.dp)
                        // Colocamos la corona sobre el admin
                    .offset(y = 54.dp)
                    .zIndex(100f)
            )
        }

        Image(
            bitmap = avatar,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .clickable { showDialog = true }
        )

        // Nombre de usuario que traemos desde el token de la API.
        PreferenceManager.getDefaultSharedPreferences(context)
            .getString("username", "Tu quién eres")?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Black,
                    fontSize = 30.sp
                )
            } ?: Text(
            text = "si",
            fontWeight = FontWeight.Black,
            fontSize = 30.sp
        )

        // Descripción, estado, acciones, etc
        Text(
            // Estas monstruosidades son actually emojis.
            text = if (isAdmin) "Eres un admin \uD83E\uDD13" else "Plebeyo \uD83E\uDD7A",
            fontWeight = FontWeight.Light,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Una manera un poco ortopédica de cerrar sesión (que funciona así que por mí chillin).
        Button(onClick = {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            // Borramos el token de las SharedPrefs.
            prefs.edit().remove("jwt").apply()
            // Y obligamos a ir de vuelta a registrarse o iniciar sesión.
            navController.navigate(Screen.Auth.route) {
                // Y borramos toda la backstack en caso de que alguien se crea listo.
                navController.popBackStack(Screen.Auth.route, inclusive = true, saveState = false)
            }
        },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Cerrar sesión", fontSize = 15.sp, fontFamily = manrope)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Elige tu avatar") },
            text = {
                // Explico lo que hace esto: selectedImage es un ImageBitmap y lo que "físicamente" se va a mostrar
                // a tiempo real en el círculo de la UI. Lo settea a la seleccionada en el ImageSelector.
                ImageSelector(bitmapMap) { image ->
                    selectedImage = image
                    // Busca el key (id) mapeado con la ImageBitmap y lo guarda en SharedPrefs para uso futuro.
                    selectedImageId = getImagenID(imageBitmap = image, bitmapMap = bitmapMap)
                    selectedImageId?.let { setImagenPreferencias(context, it) }
                    showDialog = false
                }
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
fun ImageSelector(
    bitmapMap: Map<Int, ImageBitmap>,
    onImageSelected: (ImageBitmap) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(bitmapMap.values.toList()) { bitmap ->
            Image(
                bitmap = bitmap,
                contentDescription = "Si",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 5.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onImageSelected(bitmap) }
            )
        }
    }
}

// Para obtener el R.drawable.id de un ImageBitmap.
fun getImagenID(imageBitmap: ImageBitmap, bitmapMap: Map<Int, ImageBitmap>): Int? {
    return bitmapMap.entries.find { it.value == imageBitmap }?.key
}
package com.example.bestiario_dq_app.ui.bestiario.adminscreens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bestiario_dq_app.core.utils.base64ToBitmap
import com.example.bestiario_dq_app.core.utils.encodeImageToBase64
import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.bestiario.MonstruosViewModel
import com.example.bestiario_dq_app.ui.theme.manrope
import dagger.hilt.android.qualifiers.ApplicationContext

@SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
@Composable
fun EditarMonstruo(
    viewModel: MonstruosViewModel = hiltViewModel(),
    idSeleccionado: String?,
    navController: NavController,
    @ApplicationContext context: Context
) {
    val context = LocalContext.current
    val monsterIdState = remember { mutableStateOf(TextFieldValue()) }
    val monsterNameState = remember { mutableStateOf(TextFieldValue()) }
    val monsterFamilyState = remember { mutableStateOf(TextFieldValue()) }

    // State para la imagen en Base64.
    val monsterImageState = remember { mutableStateOf(TextFieldValue()) }

    // State para la lista de atributos.
    val attributesState = remember { mutableStateListOf<Atributo>() }
    val originalAttributes = remember { attributesState.toMutableList() }

    // State para la imagen en ImageBitmap.
    val selectedImage = remember { mutableStateOf<ImageBitmap?>(null) }

    // El Monstruo buscado está ahora guardado como State en el ViewModel para acceder, y nos
    // suscribimos a ese estado para traernos los datos actualmente guardados.
    val monstruoState by viewModel.monstruo.collectAsState()

    // Nos aseguramos de que en el State del ViewModel haya un Monstruo (que buscamos con el
    // id que nos hemos traído de DetallesScreen.
    LaunchedEffect(idSeleccionado) {
        if (idSeleccionado != null) {
            viewModel.getMonstruoId(idSeleccionado)
        }
    }

    // Animación si todavía no se han cargado los datos (al hacer pruebas, vimos que hay momentos en
    // los que los datos cargaban al instante y otros que no, por las corrutinas).
    if (monstruoState == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Cargando los datos...",
                fontFamily = manrope,
                fontWeight = FontWeight.Light
            )
        }
        return
    }

    LaunchedEffect(monstruoState) {
        monstruoState?.let { monster ->
            monsterIdState.value = monsterIdState.value.copy(text = monster.idLista)
            monsterNameState.value = monsterNameState.value.copy(text = monster.nombre)
            monsterFamilyState.value = monsterFamilyState.value.copy(text = monster.familia)
            monsterImageState.value = monsterImageState.value.copy(text = monster.imagen)
        }
    }

    // Selección de imagen.
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        val bitmap = uri?.let { selectedUri ->
            val inputStream = context.contentResolver.openInputStream(selectedUri)
            inputStream?.use { stream ->
                BitmapFactory.decodeStream(stream)?.asImageBitmap()
            }
        }
        selectedImage.value = bitmap
        bitmap?.let { image ->
            val base64Image = encodeImageToBase64(image)
            // Guardamos la imagen en el estado como Base64.
            monsterImageState.value = TextFieldValue(base64Image)
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val imagePreview =
            viewModel.monstruo.value?.let { it1 -> base64ToBitmap(it1.imagen, 200, 200) }
        if (imagePreview != null) {
            Image(
                bitmap = imagePreview.asImageBitmap(),
                contentDescription = "Imagen subida",
                modifier = Modifier.width(200.dp)
            )
        }
        // Seleccionar la imagen (usando el launcher).
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Subir imagen")
        }
        TextField(
            value = monsterIdState.value.text,
            onValueChange = { monsterIdState.value = monsterIdState.value.copy(text = it) },
            label = { Text("Id (lista)") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Black,
            ),
        )

        TextField(
            value = monsterNameState.value.text,
            onValueChange = { monsterNameState.value = monsterNameState.value.copy(text = it) },
            label = { Text("Nombre del monstruo") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Black,
            ),
        )

        TextField(
            value = monsterFamilyState.value.text,
            onValueChange = { monsterFamilyState.value = monsterFamilyState.value.copy(text = it) },
            label = { Text("Familia") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Black,
            ),
        )


        // Traer los atributos (aparte porque la lógica es más compleja).
        LaunchedEffect(viewModel.monstruo.value?.atributos) {
            viewModel.monstruo.value?.atributos?.let { atributos ->
                attributesState.addAll(atributos)
            }
        }

        attributesState.forEachIndexed { index, attribute ->
            CampoAtributo(
                atributo = attribute,
                onAttributeChange = { newAttribute ->
                    attributesState[index] = newAttribute
                },
                onBorrarAtributo = {
                    attributesState.removeAt(index)
                }
            )
        }

        // Añadir un nuevo atributo.
        Button(onClick = {
            attributesState.add(Atributo(0, "", listOf(), listOf(), 0))
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Nuevo atributo")
        }

        // Se crea una nueva instancia de Monstruo con los datos proporcionados y se llama al
        // método PUT de la API, que lo actualiza por ID.
        Button(
            onClick = {
                val monstruo = viewModel.monstruo.value?.let { it ->
                    Monstruo(
                        atributos = attributesState.map { it }.toMutableList(),
                        familia = monsterFamilyState.value.text,
                        idLista = monsterIdState.value.text,
                        imagen = monsterImageState.value.text,
                        nombre = monsterNameState.value.text
                    )
                }
                if (monstruo != null) {
                    viewModel.actualizarMonstruo(monsterIdState.value.text, monstruo)
                }
                navController.navigate(Screen.Monstruos.route) {
                    navController.popBackStack(Screen.Monstruos.route, inclusive = true, saveState = false)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            // Validación de campos que consideramos obligatorios.
            enabled =
            monsterImageState.value.text.isNotBlank()
                    && monsterIdState.value.text.isNotBlank()
                    && monsterNameState.value.text.isNotBlank()
                    && monsterFamilyState.value.text.isNotBlank()
        ) {
            Text("Guardar monstruo")
        }
    }
}
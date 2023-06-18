package com.example.bestiario_dq_app.ui.bestiario.adminscreens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bestiario_dq_app.core.utils.base64ToBitmap
import com.example.bestiario_dq_app.core.utils.encodeImageToBase64
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.ui.bestiario.MonstruosViewModel
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

    // El Monstruo buscado está ahora guardado como State en el ViewModel para acceder.

    LaunchedEffect(viewModel.monstruo.value) {
        if (idSeleccionado != null) {
            viewModel.getMonstruoId(idSeleccionado)
        }

        viewModel.monstruo.value?.let { monster ->
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
            .fillMaxSize()
    ) {
        selectedImage.value?.let {
            val imagePreview =
                viewModel.monstruo.value?.let { it1 -> base64ToBitmap(it1.imagen, 200, 200) }
            if (imagePreview != null) {
                Image(
                    bitmap = imagePreview.asImageBitmap(),
                    contentDescription = "Imagen subida",
                    modifier = Modifier.width(200.dp)
                )
            }
        }
        // Seleccionar la imagen (usando el launcher).
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Select Image")
        }
        TextField(
            value = monsterIdState.value.text,
            onValueChange = { monsterIdState.value = monsterIdState.value.copy(text = it) },
            label = { Text("Monster Id") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = monsterNameState.value.text,
            onValueChange = { monsterNameState.value = monsterNameState.value.copy(text = it) },
            label = { Text("Monster Name") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = monsterFamilyState.value.text,
            onValueChange = { monsterFamilyState.value = monsterFamilyState.value.copy(text = it) },
            label = { Text("Monster Family") },
            modifier = Modifier.fillMaxWidth()
        )


        LaunchedEffect(viewModel.monstruo.value?.atributos) {
            viewModel.monstruo.value?.atributos?.let { atributos ->
                attributesState.addAll(atributos)
            }
        }

        attributesState.forEachIndexed { index, attribute ->
            CampoAtributo(
                attribute = attribute,
                onAttributeChange = { newAttribute ->
                    attributesState[index] = newAttribute
                },
                onBorrarAtributo = {
                    attributesState.removeAt(index)
                }
            )
        }

        // Añadir un nuevo atributo
        Button(onClick = {
            attributesState.add(Atributo(0, "", listOf(), listOf(), 0))
        }) {
            Text("Nuevo atributo")
        }

        Button(onClick = {
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
        }) {
            Text("Guardar monstruo")
        }
    }
}
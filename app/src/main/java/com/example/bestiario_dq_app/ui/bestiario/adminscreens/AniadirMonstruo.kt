package com.example.bestiario_dq_app.ui.bestiario.adminscreens

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bestiario_dq_app.core.utils.encodeImageToBase64
import com.example.bestiario_dq_app.data.local.AtributoEntity
import com.example.bestiario_dq_app.data.mappers.toAtributo
import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.ui.bestiario.MonstruosViewModel


@Composable
fun AniadirMonstruo(viewModel: MonstruosViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val monsterIdState = remember { mutableStateOf(TextFieldValue()) }
    val monsterNameState = remember { mutableStateOf(TextFieldValue()) }
    val monsterFamilyState = remember { mutableStateOf(TextFieldValue()) }
    // State para la imagen en Base64.
    val monsterImageState = remember { mutableStateOf(TextFieldValue()) }

    // State para la lista de atributos.
    val attributesState = remember { mutableStateListOf<Atributo>() }

    // State para la imagen en ImageBitmap.
    val selectedImage = remember { mutableStateOf<ImageBitmap?>(null) }

    // Selección de imagen
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

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()) {
        TextField(
            value = monsterIdState.value,
            onValueChange = { monsterIdState.value = it },
            label = { Text("Monster Id") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = monsterNameState.value,
            onValueChange = { monsterNameState.value = it },
            label = { Text("Monster Name") },
            modifier = Modifier.fillMaxWidth()
        )
        selectedImage.value?.let {
            Image(
                bitmap = it,
                contentDescription = "Imagen subida",
                modifier = Modifier.width(200.dp).fillMaxWidth()
            )
        }
        // Seleccionar la imagen (usando el launcher).
        Button(onClick = { launcher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
            Text("Select Image")
        }

        TextField(
            value = monsterFamilyState.value,
            onValueChange = { monsterFamilyState.value = it },
            label = { Text("Monster Family") },
            modifier = Modifier.fillMaxWidth()
        )

        // Atributos dinámicos
        attributesState.forEachIndexed { index, attribute ->
            CampoAtributo(attribute = attribute, onAttributeChange = { newAttribute ->
                attributesState[index] = newAttribute
            }, onBorrarAtributo = {
                attributesState.removeAt(index)
            })
        }

        // Añadir un nuevo atributo
        Button(onClick = {
            attributesState.add(Atributo(0, "", listOf(), listOf(), 0))
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Nuevo atributo")
        }

        Button(onClick = {
            val monster = Monstruo(
                atributos = attributesState.toList().map { it }.toMutableList(),
                familia = monsterFamilyState.value.text,
                idLista = monsterIdState.value.text,
                imagen = monsterImageState.value.text,
                nombre = monsterNameState.value.text
            )
            viewModel.newMonstruo(monster)
        }) {
            Text("Guardar monstruo")
        }
    }
}

@Composable
fun CampoAtributo(
    attribute: Atributo,
    onAttributeChange: (Atributo) -> Unit,
    onBorrarAtributo: () -> Unit
) {
    TextField(
        value = attribute.experiencia.toString(),
        onValueChange = { newExperience ->
            onAttributeChange(attribute.copy(experiencia = newExperience.toIntOrNull() ?: 0))
        },
        label = { Text("Experience") },
        modifier = Modifier.fillMaxWidth()
    )

    TextField(
        value = attribute.juego,
        onValueChange = { newGame ->
            onAttributeChange(attribute.copy(juego = newGame))
        },
        label = { Text("Game") },
        modifier = Modifier.fillMaxWidth()
    )

    TextField(
        value = attribute.lugares.joinToString(", "),
        onValueChange = { newLocations ->
            onAttributeChange(attribute.copy(lugares = newLocations.split(",").map { it.trim() }))
        },
        label = { Text("Locations") },
        modifier = Modifier.fillMaxWidth()
    )

    TextField(
        value = attribute.objetos.joinToString(", "),
        onValueChange = { newObjects ->
            onAttributeChange(attribute.copy(objetos = newObjects.split(",").map { it.trim() }))
        },
        label = { Text("Objects") },
        modifier = Modifier.fillMaxWidth()
    )

    TextField(
        value = attribute.oro.toString(),
        onValueChange = { newGold ->
            onAttributeChange(attribute.copy(oro = newGold.toIntOrNull() ?: 0))
        },
        label = { Text("Gold") },
        modifier = Modifier.fillMaxWidth()
    )

    Button(onClick = onBorrarAtributo, modifier = Modifier.fillMaxWidth()) {
        Text("Borrar atributo")
    }
}

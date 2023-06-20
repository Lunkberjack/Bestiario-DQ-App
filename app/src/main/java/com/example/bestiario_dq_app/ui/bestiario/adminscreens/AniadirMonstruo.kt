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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bestiario_dq_app.core.utils.encodeImageToBase64
import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.ui.bestiario.MonstruosViewModel
import com.example.bestiario_dq_app.ui.theme.manrope


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

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
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
                modifier = Modifier
                    .width(200.dp)
                    .fillMaxWidth()
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
            CampoAtributo(atributo = attribute, onAttributeChange = { newAttribute ->
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

        Button(
            onClick = {
                val monster = Monstruo(
                    atributos = attributesState.toList().map { it }.toMutableList(),
                    familia = monsterFamilyState.value.text,
                    idLista = monsterIdState.value.text,
                    imagen = monsterImageState.value.text,
                    nombre = monsterNameState.value.text
                )
                viewModel.newMonstruo(monster)
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar monstruo")
        }
    }
}

@Composable
fun CampoAtributo(
    atributo: Atributo,
    onAttributeChange: (Atributo) -> Unit,
    onBorrarAtributo: () -> Unit
) {
    val experienceState =
        remember { mutableStateOf(TextFieldValue(atributo.experiencia.toString())) }
    val oroState = remember { mutableStateOf(TextFieldValue(atributo.oro.toString())) }

    Text(
        text = "Atributo",
        fontFamily = manrope,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )

    TextField(
        value = experienceState.value,
        onValueChange = { newExperience ->
            val experienciaValor = newExperience.text.toIntOrNull() ?: 0
            onAttributeChange(atributo.copy(experiencia = experienciaValor))
            experienceState.value = newExperience
        },
        label = { Text("Experiencia") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    TextField(
        value = atributo.juego,
        onValueChange = { nuevoJuego ->
            onAttributeChange(atributo.copy(juego = nuevoJuego))
        },
        label = { Text("Juego") },
        modifier = Modifier.fillMaxWidth()
    )

    TextField(
        value = atributo.lugares.joinToString(", "),
        onValueChange = { nuevosLugares ->
            val lugares = nuevosLugares.split(",").map { it }
            onAttributeChange(atributo.copy(lugares = lugares))
        },
        label = { Text("Lugares") },
        modifier = Modifier.fillMaxWidth()
    )

    TextField(
        value = atributo.objetos.joinToString(","),
        onValueChange = { newObjects ->
            val objetos = newObjects.split(",").map { it }
            onAttributeChange(atributo.copy(objetos = objetos))
        },
        label = { Text("Objetos") },
        modifier = Modifier.fillMaxWidth()
    )


    TextField(
        value = experienceState.value,
        onValueChange = { newOro ->
            val oroValor = newOro.text.toIntOrNull() ?: 0
            onAttributeChange(atributo.copy(oro = oroValor))
            oroState.value = newOro
        },
        label = { Text("Oro") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    Button(onClick = onBorrarAtributo, modifier = Modifier.fillMaxWidth()) {
        Text("Borrar atributo")
    }
}

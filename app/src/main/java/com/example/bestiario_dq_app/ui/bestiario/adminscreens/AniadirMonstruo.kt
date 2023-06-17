package com.example.bestiario_dq_app.ui.bestiario.adminscreens

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.bestiario_dq_app.data.local.MonstruoEntity
import com.example.bestiario_dq_app.data.mappers.toAtributo
import com.example.bestiario_dq_app.data.mappers.toMonstruo
import com.example.bestiario_dq_app.ui.bestiario.MonstruosViewModel


@Composable
fun AniadirMonstruo(viewModel: MonstruosViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val monsterIdState = remember { mutableStateOf(TextFieldValue()) }
    val monsterNameState = remember { mutableStateOf(TextFieldValue()) }
    val monsterFamilyState = remember { mutableStateOf(TextFieldValue()) }
    val monsterImageState = remember { mutableStateOf(TextFieldValue()) }

    // State for the list of attributes
    val attributesState = remember {mutableStateListOf<AtributoEntity>()}

    // State for the selected image
    val selectedImage = remember { mutableStateOf<ImageBitmap?>(null) }

    // Launch image selection activity
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
            monsterImageState.value = TextFieldValue(base64Image)
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxSize()) {
        // Text field for monster name
        TextField(
            value = monsterIdState.value,
            onValueChange = { monsterIdState.value = it },
            label = { Text("Monster Id") }
        )
        TextField(
            value = monsterNameState.value,
            onValueChange = { monsterNameState.value = it },
            label = { Text("Monster Name") }
        )
        selectedImage.value?.let {
            Image(
                bitmap = it,
                contentDescription = "Imagen subida",
                modifier = Modifier.width(200.dp)
            )
        }
        // Button to select an image
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Select Image")
        }

        TextField(
            value = monsterFamilyState.value,
            onValueChange = { monsterFamilyState.value = it },
            label = { Text("Monster Family") }
        )

        // Dynamic attribute fields
        attributesState.forEachIndexed { index, attribute ->
            AttributeField(attribute = attribute, onAttributeChange = { newAttribute ->
                attributesState[index] = newAttribute
            }, onDeleteAttribute = {
                attributesState.removeAt(index)
            })
        }

        // Add attribute button
        Button(onClick = {
            attributesState.add(AtributoEntity(0, "", listOf(), listOf(), 0))
        }) {
            Text("Add Attribute")
        }

        // Button to save the monster with attributes
        Button(onClick = {
            // Save the monster with attributes to the database
            val monster = MonstruoEntity(
                atributos = attributesState.toList().map { it.toAtributo() },
                familia = monsterFamilyState.value.text,
                idLista = monsterIdState.value.text,
                imagen = monsterImageState.value.text,
                nombre = monsterNameState.value.text
            )
            viewModel.newMonstruo(monster.toMonstruo())
            // Perform the database insertion here
        }) {
            Text("Save Monster")
        }
    }
}

@Composable
fun AttributeField(attribute: AtributoEntity, onAttributeChange: (AtributoEntity) -> Unit, onDeleteAttribute: () -> Unit) {
    // Text field for experience
    TextField(
        value = attribute.experiencia.toString(),
        onValueChange = { newExperience ->
            onAttributeChange(attribute.copy(experiencia = newExperience.toIntOrNull() ?: 0))
        },
        label = { Text("Experience") }
    )

    // Text field for game
    TextField(
        value = attribute.juego,
        onValueChange = { newGame ->
            onAttributeChange(attribute.copy(juego = newGame))
        },
        label = { Text("Game") }
    )

    // Text field for locations
    TextField(
        value = attribute.lugares.joinToString(", "),
        onValueChange = { newLocations ->
            onAttributeChange(attribute.copy(lugares = newLocations.split(",").map { it.trim() }))
        },
        label = { Text("Locations") }
    )

    // Text field for objects
    TextField(
        value = attribute.objetos.joinToString(", "),
        onValueChange = { newObjects ->
            onAttributeChange(attribute.copy(objetos = newObjects.split(",").map { it.trim() }))
        },
        label = { Text("Objects") }
    )

    // Text field for gold
    TextField(
        value = attribute.oro.toString(),
        onValueChange = { newGold ->
            onAttributeChange(attribute.copy(oro = newGold.toIntOrNull() ?: 0))
        },
        label = { Text("Gold") }
    )

    // Button to delete the attribute
    Button(onClick = onDeleteAttribute) {
        Text("Delete Attribute")
    }
}

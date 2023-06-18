package com.example.bestiario_dq_app.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bestiario_dq_app.data.remote.responses.AuthResult
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.theme.manrope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    // Validación contraseña
    val passColor = if (viewModel.passLoginValida) Color.White else Color.Red
    val passMensajeError = if (viewModel.passLoginValida) "" else "La contraseña debe tener al menos 8 caracteres"

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is AuthResult.Autorizado -> {
                    navController.navigate(Screen.Monstruos.route) {
                        // Nos aseguramos de que la pantalla de autentificación se elimine del backstack.
                        // Si esto no fuera así, podríamos volver a acceder a ella pulsando el botón "atrás".
                        popUpTo(Screen.Auth.route) {
                            inclusive = true
                        }
                    }
                }

                is AuthResult.NoAutorizado -> {
                    Toast.makeText(
                        context,
                        "No estás autorizado",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        "Credenciales incorrectas :(",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.loginUsername,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.onLoginUsernameChanged(it))
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Black,
            ),
            placeholder = {
                Text(
                    text = "Usuario...",
                    color = Color.DarkGray,
                    fontFamily = manrope,
                    fontWeight = FontWeight.Light
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.loginPass,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.onLoginPassChanged(it))
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = passColor,
                unfocusedLabelColor = passColor,
            ),
            placeholder = {
                Text(
                    text = "Contraseña...",
                    color = Color.DarkGray,
                    fontFamily = manrope,
                    fontWeight = FontWeight.Light
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        if (!viewModel.passLoginValida) {
            Text(
                text = passMensajeError,
                color = Color.Red,
                modifier = Modifier.padding(top = 10.dp),
                fontFamily = manrope,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.onEvent(AuthUiEvent.Login)
            },
            modifier = Modifier
                .align(Alignment.End)
                .fillMaxWidth(),
            // Si la contraseña es menor de 8 caracteres, no dejamos al usuario
            // que envíe la petición.
            enabled = viewModel.passLoginValida,
        ) {
            Text(text = "Inicia sesión", fontFamily = manrope)
        }

        TextButton(
            onClick = {
                viewModel.onEvent(AuthUiEvent.OpenRegistroDialog)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Regístrate", fontFamily = manrope)
        }
    }

    if (viewModel.isRegistroDialogOpen) {
        RegistroDialog(
            onDismiss = {
                viewModel.onEvent(AuthUiEvent.CloseRegistroDialog)
            },
            onRegistro = {
                viewModel.onEvent(AuthUiEvent.Registro)
            },
            passColor = passColor
        )
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroDialog(
    passColor: Color,
    viewModel: AuthViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onRegistro: () -> Unit
) {
    val passMessage = if (viewModel.passRegistroValida) "" else "La contraseña debe tener al menos 8 caracteres"
    val state = viewModel.state // Obtener el estado del viewModel

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .height(250.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = state.registroUsername,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.onRegistroUsernameChanged(it))
                },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Black,
                ),
                placeholder = {
                    Text(
                        text = "Usuario...",
                        color = Color.DarkGray,
                        fontFamily = manrope,
                        fontWeight = FontWeight.Light
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.registroPass,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.onRegistroPassChanged(it))
                },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedLabelColor = passColor,
                    unfocusedLabelColor = passColor,
                ),
                placeholder = {
                    Text(
                        text = "Contraseña...",
                        color = Color.DarkGray,
                        fontFamily = manrope,
                        fontWeight = FontWeight.Light
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            if (!viewModel.passRegistroValida) {
                Text(
                    text = passMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 10.dp),
                    fontFamily = manrope,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRegistro,
                colors = ButtonDefaults.buttonColors(
                    //containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                // Si la contraseña es menor de 8 caracteres, no dejamos al usuario
                // que envíe la petición.
                enabled = viewModel.passRegistroValida,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Text(text = "Regístrate :)", fontFamily = manrope, fontWeight = FontWeight.Bold)
            }
        }
    }
}

package com.example.bestiario_dq_app.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bestiario_dq_app.data.remote.responses.AuthResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when(result) {
                // Si se registra/loggea correctamente o tiene token válido:
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
                        "You're not authorized",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(
                        context,
                        "An unknown error occurred",
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
            value = state.registroUsername,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.onRegistroUsernameChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Username")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.registroPass,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.onRegistroPassChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.onEvent(AuthUiEvent.Registro)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign up")
        }

        Spacer(modifier = Modifier.height(64.dp))

        TextField(
            value = state.loginUsername,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.onLoginUsernameChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Username")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.loginPass,
            onValueChange = {
                viewModel.onEvent(AuthUiEvent.onLoginPassChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.onEvent(AuthUiEvent.Login)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign in")
        }
        Button(
            onClick = {
                navController.navigate(Screen.Monstruos.route) {
                    // Nos aseguramos de que la pantalla de autentificación se elimine del backstack.
                    // Si esto no fuera así, podríamos volver a acceder a ella pulsando el botón "atrás".
                    popUpTo(Screen.Auth.route) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "A casita manín")
        }
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

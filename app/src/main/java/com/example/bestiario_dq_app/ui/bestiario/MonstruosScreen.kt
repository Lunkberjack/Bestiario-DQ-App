package com.example.bestiario_dq_app.ui.bestiario

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.preference.PreferenceManager
import com.example.bestiario_dq_app.R
import com.example.bestiario_dq_app.ui.bestiario.componentes.CartaMonstruo
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.theme.manrope
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// https://dragon-quest.org/wiki/Monsters#Families
@Composable
fun MonstruosScreen(
    navController: NavController,
    viewModel: MonstruosViewModel = hiltViewModel(),
    monstruoDao: MonstruoDao,
    @ApplicationContext context: Context,
) {
    val monstruosFlow by viewModel.monstruos.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getMonstruos(orden = "idLista", tipo = "Ascendente")
    }

    // Si la lista aún no se ha llenado, mostramos una pantalla de carga.
    if (monstruosFlow.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Un limo salvaje aparece...", fontFamily = manrope, fontWeight = FontWeight.Light)
        }
        return
    }

    val monstruos = monstruosFlow
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val isAdmin = prefs.getBoolean("admin", false)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(monstruos) { monstruo ->
                CartaMonstruo(navController, monstruo, monstruoDao)
            }
        }
    }

    if (isAdmin) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.Admin.route) },
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Cosas de admin",
                    Modifier.size(20.dp)
                )
            }
        }
    }
}


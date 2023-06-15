package com.example.bestiario_dq_app.ui.bestiario

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.preference.PreferenceManager
import com.example.bestiario_dq_app.ui.bestiario.componentes.CartaMonstruo
import com.example.bestiario_dq_app.data.local.MonstruoDao
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
    val monstruosFlow = viewModel.monstruos.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getMonstruos(orden = "idLista", tipo = "Ascendente")
    }
    val monstruos = monstruosFlow.value

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
        Text(
            text = "ADMIIIIIIIIIIIIIIIIIIIIIIIIIIIIIINNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN",
            fontSize = 40.sp
        )
    }
}


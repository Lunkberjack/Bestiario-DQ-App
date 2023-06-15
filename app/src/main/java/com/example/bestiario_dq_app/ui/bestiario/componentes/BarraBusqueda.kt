package com.example.bestiario_dq_app.ui.bestiario.componentes

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraBusqueda(context: Context) {
    SearchBar(
        query = "Search",
        onQueryChange = { },
        onSearch = { Toast.makeText(context, "Wenas", Toast.LENGTH_SHORT).show() },
        active = true,
        onActiveChange = { }) {
    }
}
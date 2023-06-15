package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import com.example.bestiario_dq_app.core.utils.base64ToBitmap
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.data.mappers.toMonstruo
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import com.example.bestiario_dq_app.ui.bestiario.componentes.CartaMonstruo
import com.example.bestiario_dq_app.ui.theme.manrope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun FavoritosScreen(
    navController: NavController,
    monstruoDao: MonstruoDao
) {
    var monstruos by remember { mutableStateOf<List<Monstruo>>(emptyList()) }

    // No se puede acceder a la base de datos en el hilo principal.
    LaunchedEffect(Unit) {
        val dao = monstruoDao
        val fetchedMonstruos = withContext(Dispatchers.IO) {
            dao.getMonstruosDao()
        }
        // Estos monstruos no vienen de la API sino de la base local.
        // Mappeamos las MonstruoEntity a Monstruo.
        monstruos = fetchedMonstruos.map { it.toMonstruo() }
    }


    LazyColumn {
        items(monstruos) { monstruo ->
            CartaMonstruo(navController = navController, monstruo = monstruo, monstruoDao = monstruoDao)
        }
    }
}

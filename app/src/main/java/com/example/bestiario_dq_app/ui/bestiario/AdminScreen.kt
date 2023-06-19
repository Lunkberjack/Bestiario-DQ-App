package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bestiario_dq_app.R
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.theme.manrope

@Composable
fun AdminScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Administración",
            fontFamily = manrope,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(7.5.dp))
        Divider()
        Spacer(modifier = Modifier.height(17.5.dp))

        ComponenteAdmin(navController, titulo = "Nuevo monstruo", ruta = Screen.AniadirMonstruo.route)
        //ComponenteAdmin(navController, titulo = "Nueva familia", ruta = Screen.AniadirMonstruo.route)
        //ComponenteAdmin(navController, titulo = "Nuevo juego", ruta = Screen.AniadirMonstruo.route)
    }
}

@Composable
fun ComponenteAdmin(
    navController: NavController,
    modifier: Modifier = Modifier,
    titulo: String,
    icono: Int = R.drawable.slimeoutline,
    ruta: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable {
                navController.navigate(ruta)
            }
            .height(80.dp)
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = titulo,
                fontFamily = manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }
    }
}

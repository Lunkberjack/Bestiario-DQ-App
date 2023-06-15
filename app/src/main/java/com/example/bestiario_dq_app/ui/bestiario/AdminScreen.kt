package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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

        Text(
            text = "Nuevo monstruo",
            fontFamily = manrope,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
        Text(
            text = "Nueva familia",
            fontFamily = manrope,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
        Text(
            text = "Nuevo juego",
            fontFamily = manrope,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
    }
}
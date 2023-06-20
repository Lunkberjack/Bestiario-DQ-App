package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.theme.manrope

@Composable
fun SettingsScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Configuración",
            fontFamily = manrope,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(7.5.dp))
        Divider()
        Spacer(modifier = Modifier.height(17.5.dp))

        ComponenteAdmin(
            navController = navController,
            titulo = "Repetir tutorial",
            ruta = Screen.OnBoarding.route
        )
    }
}
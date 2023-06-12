package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bestiario_dq_app.R
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.theme.manrope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    currentRoute: String,
    navController: NavHostController
) {
    ModalDrawerSheet(modifier = Modifier.width(275.dp)) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.logoasuh),
                contentDescription = "Logo Dragon Quest",
                modifier = Modifier.padding(top = 25.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
            )
        }
        Text(
            text = "Buscar por...",
            fontFamily = manrope,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Familia",
                    fontFamily = manrope,
                    fontWeight = FontWeight.Light,
                    fontSize = 17.5.sp
                )
            },
            icon = { Icon(Icons.Filled.Home, null) },
            selected = currentRoute == Screen.Monstruos.route,
            // TODO - Pop backstack in all of these
            onClick = { navController.navigate(Screen.Monstruos.route) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Juego", fontFamily = manrope,
                    fontWeight = FontWeight.Light,
                    fontSize = 17.5.sp
                )
            },
            icon = { Icon(Icons.Filled.Star, null) },
            selected = currentRoute == Screen.Favoritos.route,
            onClick = { navController.navigate(Screen.Favoritos.route) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        Spacer(modifier = Modifier.height(17.5.dp))
        Divider()
        Spacer(modifier = Modifier.height(17.5.dp))

        Text(
            text = "Ordenar...",
            fontFamily = manrope,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Por número",
                    fontFamily = manrope,
                    fontWeight = FontWeight.Light,
                    fontSize = 17.5.sp
                )
            },
            icon = { Icon(Icons.Filled.Home, null) },
            selected = currentRoute == Screen.Monstruos.route,
            // TODO - Pop backstack in all of these
            onClick = { navController.navigate(Screen.Monstruos.route) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Alfabéticamente", fontFamily = manrope,
                    fontWeight = FontWeight.Light,
                    fontSize = 17.5.sp
                )
            },
            icon = { Icon(Icons.Filled.Star, null) },
            selected = currentRoute == Screen.Favoritos.route,
            onClick = { navController.navigate(Screen.Favoritos.route) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(modifier = Modifier.padding(end = 20.dp, bottom = 20.dp), onClick = {
                navController.navigate(Screen.Settings.route)
            }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Ajustes",
                    Modifier.size(30.dp)
                )
            }
        }
    }
}
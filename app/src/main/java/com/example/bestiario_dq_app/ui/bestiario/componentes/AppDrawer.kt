package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.bestiario_dq_app.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    currentRoute: String,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    ModalDrawerSheet(modifier) {
        NavigationDrawerItem(
            label = { Text(text = "Monstruos") },
            icon = { Icon(Icons.Filled.Home, null) },
            selected = currentRoute == Screen.Monstruos.route,
            // TODO - Pop backstack in all of these
            onClick = { navController.navigate(Screen.Monstruos.route) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "Favoritos") },
            icon = { Icon(Icons.Filled.Star, null) },
            selected = currentRoute == Screen.Favoritos.route,
            onClick = { navController.navigate(Screen.Favoritos.route) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(text = "Perfil") },
            icon = { Icon(Icons.Filled.Person, null) },
            selected = currentRoute == Screen.Perfil.route,
            onClick = { navController.navigate(Screen.Perfil.route) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}
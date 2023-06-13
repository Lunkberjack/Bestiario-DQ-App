package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.bestiario_dq_app.R
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.bestiario.MonstruosViewModel
import com.example.bestiario_dq_app.ui.theme.manrope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    currentRoute: String,
    navController: NavHostController,
    viewModel: MonstruosViewModel = hiltViewModel()
) {
// Create a mutable state to hold the selected family
    var selectedFamily by remember { mutableStateOf("") }

    val dialogFamilias = remember { mutableStateOf(false) }
    val dialogJuegos = remember { mutableStateOf(false) }

    val familias by viewModel.familias.collectAsState(emptyList())
    viewModel.getFamilias()

    val juegos by viewModel.juegos.collectAsState(emptyList())
    viewModel.getJuegos()

    if (dialogFamilias.value) {
        Dialog(onDismissRequest = { dialogFamilias.value = false }) {
            Column(
                modifier = Modifier
                    .height(500.dp)
                    .width(300.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
            ) {
                Row(
                    Modifier
                        .weight(1f).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Elige una familia",
                        fontFamily = manrope,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 25.sp
                    )

                }
                Row(Modifier.weight(7f)) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        items(familias) { familia ->
                            FamilyOption(familia.nombre, selectedFamily) { family ->
                                selectedFamily = family
                            }
                        }
                    }
                }
                Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Button(modifier = Modifier.fillMaxWidth().padding(10.dp),
                        onClick = {
                            dialogFamilias.value = false
                            if (selectedFamily.isNotEmpty()) {
                                viewModel.viewModelScope.launch {
                                    navController.navigate(Screen.Familia.route + "/${selectedFamily}") {
                                        popUpTo(Screen.Familia.route + "/${selectedFamily}") {
                                            inclusive = true
                                        }
                                    }

                                }
                            }
                        }
                    ) {
                        Text(text = "Filtrar")
                    }
                }
            }
        }
    }

    ModalDrawerSheet(modifier = Modifier.width(275.dp)) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.logoasuh),
                contentDescription = "Logo Dragon Quest",
                modifier = Modifier.padding(top = 25.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
            )
        }
        Text(
            text = "Filtrar por...",
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
            onClick = {
                dialogFamilias.value = true
            },
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

@Composable
fun FamilyOption(
    familyName: String,
    selectedFamily: String,
    onFamilySelected: (String) -> Unit
) {
    Text(
        text = familyName,
        fontFamily = manrope,
        fontSize = 18.sp,
        fontWeight = FontWeight.Light,
        modifier = Modifier
            .clickable {
                onFamilySelected(familyName)
            }.background(
                color = if (selectedFamily == familyName) Color.LightGray else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(10.dp)
            .fillMaxWidth()

    )
}
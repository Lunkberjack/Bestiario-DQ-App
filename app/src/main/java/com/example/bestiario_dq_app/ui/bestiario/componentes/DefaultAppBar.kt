package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.room.util.query
import com.example.bestiario_dq_app.R
import com.example.bestiario_dq_app.data.remote.responses.MonstruoBusqueda
import com.example.bestiario_dq_app.ui.bestiario.BusquedaScreen
import com.example.bestiario_dq_app.ui.bestiario.MonstruosViewModel
import com.example.bestiario_dq_app.ui.bestiario.SearchViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

var listaMonstruos = listOf<MonstruoBusqueda>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    viewModel: MonstruosViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    //var searchActive by remember { mutableStateOf(false) }

    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logoasuh),
                    contentDescription = "Logo Dragon Quest",
                    modifier = Modifier
                        .height(40.dp)
                )
            }

        },
        navigationIcon = {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            drawerState.open()
                        }
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Menu Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 10.dp),
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    searchViewModel.setSearchActive(true)
                    viewModel.getMonstruos("idLista", "Ascendente")
                    viewModel.getFamilias()
                    viewModel.getJuegos()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search Icon",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 10.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
    if (searchViewModel.searchActive.value) {
        var query by remember { mutableStateOf("") }
        SearchBar(
            query = query,
            onQueryChange = {
                query = it
            },
            onSearch = {
                if (query.isNotEmpty()) {
                    viewModel.getMonstruosBusqueda()
                    listaMonstruos = viewModel.monstruosBusqueda.value.filter {
                        it.coincideBusqueda(query)
                    }
                    viewModel.getFamilias()
                    viewModel.familiasBusqueda(query)
                    viewModel.getJuegos()
                    viewModel.juegosBusqueda(query)
                }
            },
            active = searchViewModel.searchActive.value,
            onActiveChange = { searchViewModel.setSearchActive(it) },
            placeholder = { Text(text = "Buscar...") },
            trailingIcon = {
                // Si pulsamos en la X y tenemos algo escrito, lo borra.
                // Si no hay nada, sale de la búsqueda.
                IconButton(onClick = {
                    if (query.isNotEmpty()) {
                        query = ""
                    } else {
                        searchViewModel.setSearchActive(false)
                    }
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Dejar de buscar")
                }
            }
        ) {
            BusquedaScreen(navController = navController, listaMonstruos, viewModel, searchViewModel)
        }
    }
}
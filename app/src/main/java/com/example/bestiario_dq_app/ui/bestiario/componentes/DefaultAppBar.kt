package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(scrollBehavior: TopAppBarScrollBehavior /*onSearchClicked: () -> Unit*/) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Bestiario",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { /*onSearchClicked()*/ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Icon",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(
                onClick = { /*onSearchClicked()*/ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Blue
        )
    )
}
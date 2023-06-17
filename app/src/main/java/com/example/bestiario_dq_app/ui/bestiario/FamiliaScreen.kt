package com.example.bestiario_dq_app.ui.bestiario

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bestiario_dq_app.ui.bestiario.componentes.CartaMonstruo
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.ui.theme.manrope
import dagger.hilt.android.qualifiers.ApplicationContext

// https://dragon-quest.org/wiki/Monsters#Families

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamiliaScreen(
    familia: String?,
    navController: NavController,
    monstruoDao: MonstruoDao,
    @ApplicationContext context: Context,
    viewModel: MonstruosViewModel = hiltViewModel()
) {
    val monstruos by viewModel.monstruos.collectAsState(emptyList())
    if (familia != null) {
        viewModel.filtrarFamilia(familia)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            buildAnnotatedString {
                append("Familia\n")
                withStyle(
                    style = SpanStyle(
                        fontFamily = manrope,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                ) {
                    append(familia)
                }
            },
            fontFamily = manrope,
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        LazyColumn {
            items(monstruos) { monstruo ->
                CartaMonstruo(navController, monstruo, monstruoDao)
            }
        }
    }
}

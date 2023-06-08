package com.example.bestiario_dq_app.ui.bestiario

import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import com.example.bestiario_dq_app.ui.auth.AuthState
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestiarioViewModel @Inject constructor (
    private val repository: MonstruosRepository
) : ViewModel() {
    var state by mutableStateOf(AuthState())

    fun onEvent(event: BestiarioEvent) {
        when(event) {
            is BestiarioEvent.onTraerMonstruos -> {
                getMonstruos()
            }
        }
    }

    private fun newMonstruo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.newMonstruo(
                nombre = state.registroUsername,
                // Convertir la imagen a Base64
                imagen = state.registroPass
            )
        }
    }

    public fun getMonstruos() {
        viewModelScope.launch {
            for (monstruo in repository.getMonstruos()) {
                Log.d("MONSTRUO", monstruo.nombre)
            }
        }
    }
}

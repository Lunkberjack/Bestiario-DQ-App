package com.example.bestiario_dq_app.ui.bestiario

import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import com.example.bestiario_dq_app.ui.auth.AuthState
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonstruosViewModel @Inject constructor (
    private val repository: MonstruosRepository
) : ViewModel() {
    var state by mutableStateOf(AuthState())
    var monstruos by mutableStateOf<List<Monstruo>>(emptyList())

    private val _monstruo = MutableStateFlow<Monstruo?>(null)
    val monstruo: StateFlow<Monstruo?> = _monstruo

    private val _monstruoSeleccionado = mutableStateOf("")
    val monstruoSeleccionado: State<String> = _monstruoSeleccionado

    fun setMonstruoSeleccionado(monstruoId: String) {
        _monstruoSeleccionado.value = monstruoId
        Log.d("MonstruosViewModel", "Selected monster ID: $monstruoId")
    }

    // Búsqueda
    private val _promptState = MutableStateFlow("")
    val promptState = _promptState.asStateFlow()

    private val _buscando = MutableStateFlow(false)
    val buscando = _buscando.asStateFlow()

    fun onEvent(event: MonstruosEvent) {
        when(event) {
            is MonstruosEvent.onTraerMonstruos -> {
                getMonstruos()
            }
        }
    }

    private fun newMonstruo() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.newMonstruo(
                idLista = "",
                nombre = state.registroUsername,
                // Convertir la imagen a Base64
                imagen = state.registroPass
            )
        }
    }

    public fun getMonstruos() {
        viewModelScope.launch {
            monstruos = repository.getMonstruos()
        }
    }

    fun getMonstruoId(monstruoId: String) {
        viewModelScope.launch {
            val fetchedMonstruo = repository.getMonstruoIdLista(monstruoId)
            _monstruo.value = fetchedMonstruo
        }
    }
}

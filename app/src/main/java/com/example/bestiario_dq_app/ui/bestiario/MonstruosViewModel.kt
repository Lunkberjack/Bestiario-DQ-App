package com.example.bestiario_dq_app.ui.bestiario

import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import com.example.bestiario_dq_app.ui.auth.AuthState
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bestiario_dq_app.data.remote.responses.Familia
import com.example.bestiario_dq_app.data.remote.responses.Juego
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonstruosViewModel @Inject constructor(
    private val repository: MonstruosRepository
) : ViewModel() {
    var state by mutableStateOf(AuthState())

    private val _monstruos = MutableStateFlow<List<Monstruo>>(emptyList())
    val monstruos: StateFlow<List<Monstruo>> = _monstruos

    private val _familias = MutableStateFlow<List<Familia>>(emptyList())
    val familias: StateFlow<List<Familia>> = _familias

    private val _juegos = MutableStateFlow<List<Juego>>(emptyList())
    val juegos: StateFlow<List<Juego>> = _juegos

    private val _monstruo = MutableStateFlow<Monstruo?>(null)
    val monstruo: StateFlow<Monstruo?> = _monstruo

    private val _monstruoSeleccionado = mutableStateOf("")
    val monstruoSeleccionado: State<String> = _monstruoSeleccionado

    fun onEvent(event: MonstruosEvent) {
        when (event) {
            is MonstruosEvent.onTraerMonstruos -> {
                getMonstruos()
            }

            is MonstruosEvent.onTraerFamilia -> {
                val familia = event.familia
                viewModelScope.launch {
                    filtrarMonstruos(familia)
                }
            }
        }
    }

    fun filtrarMonstruos(familia: String) {
        viewModelScope.launch {
            val nuevaLista = repository.filtrarFamilia(familia)
            _monstruos.value = nuevaLista
            Log.d("MonstruosViewModel", "Filtered monsters: $nuevaLista")
        }
    }

    fun setMonstruoSeleccionado(monstruoId: String) {
        _monstruoSeleccionado.value = monstruoId
        Log.d("MonstruosViewModel", "Selected monster ID: $monstruoId")
    }

    // Búsqueda
    private val _promptState = MutableStateFlow("")
    val promptState = _promptState.asStateFlow()

    private val _buscando = MutableStateFlow(false)
    val buscando = _buscando.asStateFlow()

    /*private fun newMonstruo() {
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

     */

    fun getMonstruos() {
        // Lógica para obtener la lista según la familia seleccionada
        viewModelScope.launch {
            val nuevaLista = repository.getMonstruos()
            _monstruos.value = nuevaLista
        }
    }

    fun getFamilias() {
        viewModelScope.launch {
            _familias.value = repository.getFamilias()
        }
    }

    fun getJuegos() {
        viewModelScope.launch {
            _monstruos.value = repository.getMonstruos()
        }
    }

    fun getMonstruoId(monstruoId: String) {
        viewModelScope.launch {
            val fetchedMonstruo = repository.getMonstruoIdLista(monstruoId)
            _monstruo.value = fetchedMonstruo
        }
    }
}

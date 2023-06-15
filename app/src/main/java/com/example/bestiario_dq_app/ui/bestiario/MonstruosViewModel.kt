package com.example.bestiario_dq_app.ui.bestiario

import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import com.example.bestiario_dq_app.ui.auth.AuthState
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestiario_dq_app.core.utils.TipoOrden
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.data.mappers.toMonstruo
import com.example.bestiario_dq_app.data.remote.responses.Familia
import com.example.bestiario_dq_app.data.remote.responses.Juego
import com.example.bestiario_dq_app.data.remote.responses.Monstruo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonstruosViewModel @Inject constructor(
    private val repository: MonstruosRepository,
    private val monstruoDao: MonstruoDao,
) : ViewModel() {
    var state by mutableStateOf(AuthState())

    private val _monstruos = MutableStateFlow<List<Monstruo>>(emptyList())
    val monstruos: StateFlow<List<Monstruo>> = _monstruos

    private val _familias = MutableStateFlow<List<Familia>>(emptyList())
    val familias: StateFlow<List<Familia>> = _familias.asStateFlow()

    private val _juegos = MutableStateFlow<List<Juego>>(emptyList())
    val juegos: StateFlow<List<Juego>> = _juegos.asStateFlow()

    private val _monstruo = MutableStateFlow<Monstruo?>(null)
    val monstruo: StateFlow<Monstruo?> = _monstruo

    private val _juego = MutableStateFlow<Juego?>(null)
    val juego: StateFlow<Juego?> = _juego

    private val _monstruoSeleccionado = mutableStateOf("")
    val monstruoSeleccionado: State<String> = _monstruoSeleccionado

    fun onEvent(event: MonstruosEvent) {

    }

    fun filtrarFamilia(familia: String) {
        viewModelScope.launch {
           repository.filtrarFamilia(familia).collect { listaFiltrada ->
               _monstruos.value = listaFiltrada
               _monstruos.emit(listaFiltrada)
           }
        }
    }

    fun filtrarJuego(juego: String) {
        viewModelScope.launch {
            repository.filtrarJuego(juego).collect { listaFiltrada ->
                _monstruos.value = listaFiltrada
            }
        }
    }

    fun setMonstruoSeleccionado(monstruoId: String) {
        _monstruoSeleccionado.value = monstruoId
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

    fun getMonstruos(orden: String?, tipo: String?) {
        // Lógica para obtener la lista según el orden seleccionado
        viewModelScope.launch {
            val nuevosMonstruos = repository.getMonstruos(orden, tipo)
            _monstruos.emitAll(nuevosMonstruos)
        }
    }

   fun getFamilias() {
       viewModelScope.launch {
           repository.getFamilias().collect { listaFamilias ->
               _familias.value = listaFamilias
           }
       }
    }

    fun getJuegos() {
        viewModelScope.launch {
            repository.getJuegos().collect { listaJuegos ->
                _juegos.value = listaJuegos
            }
        }
    }

    fun getJuego(abr: String) {
        viewModelScope.launch {
            _juego.value = repository.getJuego(abr)
        }
    }

    fun getMonstruoId(monstruoId: String) {
        viewModelScope.launch {
            val fetchedMonstruo = repository.getMonstruoIdLista(monstruoId)
            _monstruo.value = fetchedMonstruo
        }
    }

    fun getMonstruoRoom(id: String) {
        viewModelScope.launch {
            val monstruo = monstruoDao.getMonstruoId(id)
            if (monstruo != null) {
                _monstruo.value = monstruo.toMonstruo()
            }
        }
    }
}
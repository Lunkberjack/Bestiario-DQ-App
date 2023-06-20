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
import com.example.bestiario_dq_app.data.remote.responses.MonstruoBusqueda
import com.example.bestiario_dq_app.ui.auth.AuthUiEvent
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

    private val _monstruos = MutableStateFlow(listOf<Monstruo>())
    val monstruos: StateFlow<List<Monstruo>> = _monstruos.asStateFlow()

    private var _monstruosBusqueda = MutableStateFlow(listOf<MonstruoBusqueda>())
    val monstruosBusqueda: StateFlow<List<MonstruoBusqueda>> = _monstruosBusqueda.asStateFlow()

    private val _familias = MutableStateFlow(listOf<Familia>())
    val familias: StateFlow<List<Familia>> = _familias.asStateFlow()

    private val _juegos = MutableStateFlow(listOf<Juego>())
    val juegos: StateFlow<List<Juego>> = _juegos.asStateFlow()

    private val _monstruo = MutableStateFlow<Monstruo?>(null)
    val monstruo: StateFlow<Monstruo?> = _monstruo

    private val _juego = MutableStateFlow<Juego?>(null)
    val juego: StateFlow<Juego?> = _juego

    private val _monstruoSeleccionado = mutableStateOf("")
    val monstruoSeleccionado: State<String> = _monstruoSeleccionado

    fun getMonstruosBusqueda() {
        viewModelScope.launch {
            val nuevosMonstruos = repository.getMonstruosBusqueda("nombre", "Ascendente")
            _monstruosBusqueda.emitAll(nuevosMonstruos)
        }
    }

    fun familiasBusqueda(query: String) {
        viewModelScope.launch {
            val busqueda = mutableListOf<Familia>()
            // Primero aparecerán los que EMPIEZAN por la query.
            for (each in familias.value) {
                if (each.nombre.startsWith(query, ignoreCase = true)) {
                    busqueda.add(each)
                } else {
                    busqueda.remove(each)
                }
            }
            // Después, los que la CONTIENEN.
            for (each in familias.value) {
                if (each.nombre.contains(
                        query,
                        ignoreCase = true
                    ) && !(each.nombre.startsWith(query, ignoreCase = true))
                ) {
                    busqueda.add(each)
                } else if (!(each.nombre.startsWith(query, ignoreCase = true))) {
                    busqueda.remove(each)
                }
                //_familias.value = busqueda
                _familias.emit(busqueda)
            }
        }
    }

    fun juegosBusqueda(query: String) {
        viewModelScope.launch {
            val busqueda = mutableListOf<Juego>()
            // Primero aparecerán los que EMPIEZAN por la query.
            for (each in juegos.value) {
                if (each.nombre.startsWith(query, ignoreCase = true) || each.abr.startsWith(
                        query,
                        ignoreCase = true
                    )
                ) {
                    busqueda.add(each)
                } else {
                    busqueda.remove(each)
                }
            }
            // Después, los que la CONTIENEN.
            for (each in juegos.value) {
                if (each.nombre.contains(
                        query,
                        ignoreCase = true
                    ) && !(each.nombre.startsWith(query, ignoreCase = true))
                ) {
                    busqueda.add(each)
                } else if (!(each.nombre.startsWith(query, ignoreCase = true))) {
                    busqueda.remove(each)
                }
                _juegos.emit(busqueda)
            }
        }
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

    fun actualizarNombre(nombre: String) {
        _monstruo.value?.nombre = nombre
    }

    fun actualizarFamilia(familia: String) {
        _monstruo.value?.familia = familia
    }

    fun actualizarIdLista(idLista: String) {
        _monstruo.value?.idLista = idLista
    }

    // Búsqueda
    private val _promptState = MutableStateFlow("")
    val promptState = _promptState.asStateFlow()

    private val _buscando = MutableStateFlow(false)
    val buscando = _buscando.asStateFlow()

    fun newMonstruo(monstruo: Monstruo) {
        viewModelScope.launch {
            repository.newMonstruo(monstruo)
        }
    }

    fun actualizarMonstruo(idLista: String, monstruo: Monstruo) {
        viewModelScope.launch {
            repository.actualizarMonstruo(idLista, monstruo)
        }
    }

    fun borrarMonstruo(idLista: String) {
        viewModelScope.launch {
            repository.borrarMonstruo(idLista)
        }
    }

    fun getMonstruos(orden: String?, tipo: String?) {
        // Lógica para obtener la lista según el orden seleccionado
        viewModelScope.launch {
            try {
                val nuevosMonstruos = repository.getMonstruos(orden, tipo)
                _monstruos.emitAll(nuevosMonstruos)
            } catch (e: Exception) {
                e.printStackTrace()
            }
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
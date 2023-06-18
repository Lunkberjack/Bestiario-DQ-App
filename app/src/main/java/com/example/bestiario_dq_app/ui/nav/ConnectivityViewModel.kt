package com.example.bestiario_dq_app.ui.nav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    private val repository: MonstruosRepository
) : ViewModel(){
    private val _isConnected = MutableStateFlow(true) // Valor inicial (asume que hay conexión)
    val isConnected = _isConnected.asStateFlow()

    // Método para verificar la conexión a la API
    fun checkApiConnectivity() {
        _isConnected.value = try {
            // Realiza una solicitud a la API con un tiempo de espera corto
            // Si la solicitud se realiza correctamente, se asume que hay conexión
            // Si se produce una excepción SocketTimeoutException, se asume que no hay conexión
            performApiRequest()
            true
        } catch (e: SocketTimeoutException) {
            false
        }
    }

    private fun performApiRequest() {
        viewModelScope.launch {
            repository.getMonstruoIdLista("0001")
        }
    }
}

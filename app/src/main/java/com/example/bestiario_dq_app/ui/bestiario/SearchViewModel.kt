package com.example.bestiario_dq_app.ui.bestiario

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val _searchActive = mutableStateOf(false)
    val searchActive: State<Boolean> = _searchActive

    fun setSearchActive(active: Boolean) {
        _searchActive.value = active
    }
}
package com.example.bestiario_dq_app.ui.bestiario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.bestiario_dq_app.data.local.MonstruoEntity
import com.example.bestiario_dq_app.data.mappers.toMonstruo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
/*
@HiltViewModel
class MonstruosRoomViewModel @Inject constructor(
    pager: Pager<Int, MonstruoEntity>
) : ViewModel() {
    val monstruosFlow = pager.flow.map { data ->
        data.map { it.toMonstruo() }
    }.cachedIn(viewModelScope)
}

 */
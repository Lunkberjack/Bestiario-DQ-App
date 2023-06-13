package com.example.bestiario_dq_app.ui.bestiario

sealed class MonstruosEvent {
    object onTraerMonstruos: MonstruosEvent()
    data class onTraerFamilia(val familia: String) : MonstruosEvent()
}
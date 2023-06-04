package com.example.bestiario_dq_app.data.remote.responses

/**
 * Clase wrapper que da información sobre si estamos autorizados,
 * no, o ha ocurrido un error desconocido.
 */
sealed class AuthResult<T>(val data: T? = null) {
    class Autorizado<T>(data: T? = null): AuthResult<T>(data)
        class NoAutorizado<T>: AuthResult<T>()
    class UnknownError<T>: AuthResult<T>()
}
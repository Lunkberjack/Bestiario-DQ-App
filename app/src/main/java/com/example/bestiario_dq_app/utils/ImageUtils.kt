package com.example.bestiario_dq_app.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

// TODO - Aniadir a la documentación:
//  https://medium.com/@gangulysourik/a-definitive-guide-to-clean-architecture-in-android-with-mvvm-d74a0533ef2c

/**
 * Tenemos las imágenes guardadas como Base64 en MongoDB Atlas, así que al traerlas tenemos que convertirlas
 * a Bitmap para poder agregarlas a el elemento Composable Image.
 */
fun base64ToBitmap(base64: String): Bitmap? {
    val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}
package com.example.bestiario_dq_app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// TODO - Aniadir a la documentación:
//  https://medium.com/@gangulysourik/a-definitive-guide-to-clean-architecture-in-android-with-mvvm-d74a0533ef2c

/**
 * Tenemos las imágenes guardadas como Base64 en MongoDB Atlas, así que al traerlas tenemos que convertirlas
 * a Bitmap para poder agregarlas a el elemento Composable Image.

fun base64ToBitmap(base64: String): Bitmap? {
    val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}
*/

fun base64ToBitmap(base64: String, desiredWidth: Int, desiredHeight: Int): Bitmap? {
    val decodedBytes = Base64.decode(base64, Base64.DEFAULT)

    val options = BitmapFactory.Options().apply {
        // Set inJustDecodeBounds to true to only decode the bitmap's bounds
        inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size, this)

        // Calculate the inSampleSize based on the desired dimensions
        inSampleSize = calculateInSampleSize(outWidth, outHeight, desiredWidth, desiredHeight)

        // Set inJustDecodeBounds to false to decode the actual bitmap
        inJustDecodeBounds = false
    }

    // Decode the bitmap with the calculated inSampleSize
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size, options)
}

fun calculateInSampleSize(actualWidth: Int, actualHeight: Int, desiredWidth: Int, desiredHeight: Int): Int {
    var inSampleSize = 1

    if (actualHeight > desiredHeight || actualWidth > desiredWidth) {
        val halfHeight = actualHeight / 2
        val halfWidth = actualWidth / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both dimensions larger than the desired dimensions
        while (halfHeight / inSampleSize >= desiredHeight && halfWidth / inSampleSize >= desiredWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}


suspend fun decodeImageBitmap(imageRes: Int, context: Context): ImageBitmap = withContext(Dispatchers.Default) {
    val bitmap = BitmapFactory.decodeResource(context.resources, imageRes)
    return@withContext bitmap.asImageBitmap()
}
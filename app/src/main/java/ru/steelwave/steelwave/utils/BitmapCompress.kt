package ru.steelwave.steelwave.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import java.io.ByteArrayOutputStream

fun compressImage(bitmap: Bitmap, quality: Int): Bitmap {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    val byteArray = stream.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun getBitmapSizeInBytes(bitmap: Bitmap): Long {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        return bitmap.allocationByteCount.toLong()
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
        return bitmap.byteCount.toLong()
    }
    return (bitmap.rowBytes * bitmap.height).toLong()
}
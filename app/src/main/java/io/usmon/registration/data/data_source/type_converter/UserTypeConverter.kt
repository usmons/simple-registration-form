package io.usmon.registration.data.data_source.type_converter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

// Created by Usmon Abdurakhmanv on 6/9/2022.

class UserTypeConverter {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray {
        return bitmap?.let {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.toByteArray()
        } ?: ByteArray(0)
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap? {
        if (byteArray.isEmpty()) return null
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}
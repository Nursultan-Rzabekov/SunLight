package com.example.sunlightdesign.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns


fun getFileName(context: Context, uri: Uri): String? {
    return context.contentResolver
        .query(uri, null, null, null, null)?.use {
            cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                cursor.getString(nameIndex)
        }
}

fun getFileSizeInLong(context: Context, uri: Uri): Long? {
    return context.contentResolver
        .query(uri, null, null, null, null)?.use {
                cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            cursor.moveToFirst()
            cursor.getLong(sizeIndex)
        }
}
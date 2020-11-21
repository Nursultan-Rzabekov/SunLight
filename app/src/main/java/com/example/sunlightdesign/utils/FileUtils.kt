package com.example.sunlightdesign.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.app.ActivityCompat
import com.example.sunlightdesign.BuildConfig
import com.example.sunlightdesign.utils.Constants.Companion.PERMISSIONS_REQUEST_WRITE_STORAGE


fun getFileName(context: Context, uri: Uri): String? {
    return context.contentResolver
        .query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        }
}

fun getFileSizeInLong(context: Context, uri: Uri): Long? {
    return context.contentResolver
        .query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            cursor.moveToFirst()
            cursor.getLong(sizeIndex)
        }
}

fun checkFilePermission(activity: Activity) : Boolean =
    if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
        PackageManager.PERMISSION_GRANTED &&
        activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
        PackageManager.PERMISSION_GRANTED) {
        true
    } else {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
                ),
            PERMISSIONS_REQUEST_WRITE_STORAGE
        )
        false
    }

fun getFileUrlPath(fileSubPath: String) : String {
    return BuildConfig.BASE_URL_FILE + fileSubPath
}
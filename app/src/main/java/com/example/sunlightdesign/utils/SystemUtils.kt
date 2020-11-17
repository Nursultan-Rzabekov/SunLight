package com.example.sunlightdesign.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun saveToClipboard(context: Context, text: String, label: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}
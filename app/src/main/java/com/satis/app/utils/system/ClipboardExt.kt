package com.satis.app.utils.system

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.toClipboard(label: String = "content", text: String) {
    getSystemService(ClipboardManager::class.java).primaryClip = ClipData.newPlainText(label, text)
}
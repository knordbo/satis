package com.satis.app.common

import android.content.Context
import androidx.content.edit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class Prefs(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, 0)

    fun getUniqueId(): String = UNIQUE_USER_ID.let { key ->
        if (sharedPreferences.contains(key)) {
            sharedPreferences.getString(key, "")
        } else {
            val id = UUID.randomUUID().toString()
            sharedPreferences.edit {
                putString(key, id)
            }
            id
        }
    }

    fun log(tag: String, message: String) {
        val time = SimpleDateFormat("dd.MM.YY HH:mm", Locale.US).format(Date())
        val line = "[$time] [$tag] $message"
        val currentLog: String? = sharedPreferences.getString(LOG, null)
        sharedPreferences.edit {
            putString(LOG, if (currentLog == null) line else "$currentLog\n$line")
        }
    }

    fun getLog(): String = sharedPreferences.getString(LOG, "")
}

private const val SHARED_PREFS = "satis.shared.prefs"
private const val UNIQUE_USER_ID = "UNIQUE_USER_ID"
private const val LOG = "LOG"
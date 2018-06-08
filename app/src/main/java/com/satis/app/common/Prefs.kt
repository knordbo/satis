package com.satis.app.common

import android.content.Context
import androidx.content.edit
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
}

private const val SHARED_PREFS = "satis.shared.prefs"
private const val UNIQUE_USER_ID = "UNIQUE_USER_ID"
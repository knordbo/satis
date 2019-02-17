package com.satis.app.common.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.UUID

class DefaultPrefs(context: Context) : Prefs {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFS, 0)

    override val uniqueId: String
        get() {
            val existingValue = sharedPreferences.getString(UNIQUE_USER_ID, null)
            return if (existingValue != null) {
                existingValue
            } else {
                val id = UUID.randomUUID().toString()
                sharedPreferences.edit {
                    putString(UNIQUE_USER_ID, id)
                }
                id
            }
        }

}

private const val SHARED_PREFS = "satis.shared.prefs"
private const val UNIQUE_USER_ID = "UNIQUE_USER_ID"
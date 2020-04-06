package com.satis.app.common.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.satis.app.common.annotations.SharedPrefsName
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsImpl @Inject constructor(
    context: Context,
    @SharedPrefsName prefsName: String
) : Prefs {

  private val sharedPreferences: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

  override val userId: UserId
    get() {
      val existingValue = sharedPreferences.getString(UNIQUE_USER_ID, null)
      return UserId(if (existingValue != null) {
        existingValue
      } else {
        val id = UUID.randomUUID().toString()
        sharedPreferences.edit {
          putString(UNIQUE_USER_ID, id)
        }
        id
      })
    }

  override var theme: Theme
    get() {
      val currentTheme = sharedPreferences.getString(THEME, null)
      return Theme.values()
          .firstOrNull { theme ->
            theme.themeName == currentTheme
          } ?: Theme.SYSTEM
    }
    set(value) {
      sharedPreferences.edit {
        putString(THEME, value.themeName)
      }
    }
}

private const val UNIQUE_USER_ID = "UNIQUE_USER_ID"
private const val THEME = "THEME"
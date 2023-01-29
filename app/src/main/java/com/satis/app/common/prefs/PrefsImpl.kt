package com.satis.app.common.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.satis.app.common.annotations.SharedPrefsName
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsImpl @Inject constructor(
  @ApplicationContext context: Context,
  @SharedPrefsName prefsName: String,
) : Prefs {

  private val sharedPreferences: SharedPreferences =
    context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

  override val currentAccountId: String
    get() {
      val existingValue = sharedPreferences.getString(UNIQUE_ACCOUNT_ID, null)
      return if (existingValue != null) {
        existingValue
      } else {
        val id = UUID.randomUUID().toString()
        sharedPreferences.edit {
          putString(UNIQUE_ACCOUNT_ID, id)
        }
        id
      }
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

  override var notificationToken: String?
    get() = sharedPreferences.getString(NOTIFICATION_TOKEN, null)
    set(value) {
      sharedPreferences.edit {
        putString(NOTIFICATION_TOKEN, value)
      }
    }
}

private const val UNIQUE_ACCOUNT_ID = "UNIQUE_ACCOUNT_ID"
private const val THEME = "THEME"
private const val NOTIFICATION_TOKEN = "NOTIFICATION_TOKEN"
package com.satis.app.common.prefs

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.satis.app.common.prefs.Theme.DARK
import com.satis.app.common.prefs.Theme.LIGHT
import com.satis.app.common.prefs.Theme.SYSTEM

enum class Theme(val themeName: String) {
    SYSTEM("system"),
    LIGHT("light"),
    DARK("dark")
}

fun Theme.apply() = AppCompatDelegate.setDefaultNightMode(when (this) {
    SYSTEM -> if (Build.VERSION.SDK_INT > 28) MODE_NIGHT_FOLLOW_SYSTEM else MODE_NIGHT_AUTO_BATTERY
    LIGHT -> MODE_NIGHT_NO
    DARK -> MODE_NIGHT_YES
})
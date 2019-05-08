package com.satis.app.common.prefs

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.NightMode

enum class Theme(val themeName: String, @NightMode val nightMode: Int) {
    SYSTEM("system", MODE_NIGHT_FOLLOW_SYSTEM),
    LIGHT("light", MODE_NIGHT_NO),
    DARK("dark", MODE_NIGHT_YES)
}

fun Theme.apply() = AppCompatDelegate.setDefaultNightMode(nightMode)
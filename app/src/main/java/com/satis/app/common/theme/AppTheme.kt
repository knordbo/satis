package com.satis.app.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
  colors: Colors? = null,
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colors = colors ?: if (isSystemInDarkTheme()) darkColors else lightColors,
    content = content
  )
}

private val lightColors = lightColors(
  primary = Color(0x3f51b5),
  primaryVariant = Color(0xFFFFFF),
  secondary = Color(0x607d8b)
)

private val darkColors = darkColors(
  primary = Color(0x757de8),
  primaryVariant = Color(0x000000),
  secondary = Color(0x607d8b)
)

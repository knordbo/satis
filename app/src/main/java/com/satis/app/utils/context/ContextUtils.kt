package com.satis.app.utils.context

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.getSystemService

fun Context.requireDrawable(@DrawableRes id: Int) = requireNotNull(getDrawable(id)) {
  "no drawable found for id: $id"
}

inline fun <reified T : Any> Context.requireSystemService(): T = requireNotNull(getSystemService()) {
  "no system service found for: ${T::class}"
}
package com.satis.app.utils.context

import android.content.Context
import androidx.core.content.getSystemService

inline fun <reified T : Any> Context.requireSystemService(): T = requireNotNull(getSystemService()) {
  "no system service found for: ${T::class}"
}
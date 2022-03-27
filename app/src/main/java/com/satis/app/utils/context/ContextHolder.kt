package com.satis.app.utils.context

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ContextHolder {
  lateinit var context: Context
}

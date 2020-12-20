package com.satis.app.common.logging

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogEntry(
  val id: Long = 0,
  val timestamp: Long,
  val tag: String,
  val message: String
) : Parcelable
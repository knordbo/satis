package com.satis.app.feature.account.ui

import com.satis.app.common.logging.LogEntry
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private val dateFormatter by lazy { SimpleDateFormat("dd.MM.yy HH:mm", Locale.US) }
private val localDateFormatter by lazy { DateTimeFormatter.ofPattern("dd.MM.yy", Locale.US) }

val LogEntry.formatted: String
  get() {
    val time = dateFormatter.format(Date(timestamp))
    return "[$time] [$tag] $message"
  }

val LocalDate.formatted: String
  get() = format(localDateFormatter)
package com.satis.app.feature.account.ui

import com.satis.app.common.logging.LogEntry
import java.time.ZoneId
import java.util.*

fun List<LogEntry>.toListItems(): List<LogItem> =
  groupBy { logEntry ->
    logEntry.timestamp.toLocalDate()
  }.flatMap { (day, logEntriesInDay) ->
    listOf(DateHeaderItem(day)) + logEntriesInDay.map(::LogEntryItem)
  }

private fun Long.toLocalDate() = Date(this).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
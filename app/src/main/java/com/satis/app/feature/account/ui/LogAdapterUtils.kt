package com.satis.app.feature.account.ui

import com.satis.app.common.logging.LogEntry
import com.satis.app.feature.account.ui.DateHeaderViewBinder.DateHeaderViewHolder
import com.satis.app.feature.account.ui.LogViewBinder.LogViewHolder
import java.time.ZoneId
import java.util.*

fun LogViewHolder.bind(item: LogEntryAdapterItem) {
  bind(message = item.logEntry.formatted)
}

fun DateHeaderViewHolder.bind(item: DateHeaderAdapterItem) {
  bind(header = item.date.formatted)
}

fun List<LogEntry>.toAdapterItems(): List<LogAdapterItem> =
    groupBy { logEntry ->
      logEntry.timestamp.toLocalDate()
    }.flatMap { (day, logEntriesInDay) ->
      listOf(DateHeaderAdapterItem(day)) + logEntriesInDay.map(::LogEntryAdapterItem)
    }

private fun Long.toLocalDate() = Date(this).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
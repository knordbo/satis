package com.satis.app.feature.account.ui

import com.satis.app.common.logging.LogEntry
import java.time.LocalDate

sealed class LogAdapterItem {
  abstract val id: Any
}

data class LogEntryAdapterItem(val logEntry: LogEntry) : LogAdapterItem() {
  override val id: Any = logEntry.id
}

data class DateHeaderAdapterItem(val date: LocalDate) : LogAdapterItem() {
  override val id: Any = date
}

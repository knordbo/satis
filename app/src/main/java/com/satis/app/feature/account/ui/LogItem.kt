package com.satis.app.feature.account.ui

import com.satis.app.common.logging.LogEntry
import java.time.LocalDate

sealed class LogItem

data class LogEntryItem(val logEntry: LogEntry) : LogItem()

data class DateHeaderItem(val date: LocalDate) : LogItem()

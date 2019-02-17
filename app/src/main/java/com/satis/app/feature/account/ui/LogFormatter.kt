package com.satis.app.feature.account.ui

import com.satis.app.common.logging.LogEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormatter by lazy { SimpleDateFormat("dd.MM.YY HH:mm", Locale.US) }

val LogEntry.formatted: String
    get() {
        val time = dateFormatter.format(Date(timestamp))
        return "[$time] [$tag] $message"
    }
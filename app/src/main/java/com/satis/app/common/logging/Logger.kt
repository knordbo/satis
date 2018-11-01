package com.satis.app.common.logging

import kotlinx.coroutines.channels.ReceiveChannel

interface Logger {
    fun log(tag: String, message: String)
    fun streamLogs(): ReceiveChannel<List<LogEntry>>
}
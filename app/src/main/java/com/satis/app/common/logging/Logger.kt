package com.satis.app.common.logging

import kotlinx.coroutines.flow.Flow

interface Logger {
    fun log(tag: String, message: String)
    fun streamLogs(): Flow<List<LogEntry>>
    suspend fun searchLogs(query: String): List<LogEntry>
}
package com.satis.app.common.logging

import android.util.Log
import com.satis.app.common.annotations.Io
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.flow.asFlow
import javax.inject.Inject

class PersistedLogger @Inject constructor(
        private val logDao: LogDao,
        @Io private val io: CoroutineDispatcher
) : Logger {

    override fun log(tag: String, message: String) {
        Log.d(tag, message)
        val timestamp = System.currentTimeMillis()
        GlobalScope.launch(io) {
            logDao.insertLog(LogEntity(
                    timestamp = timestamp,
                    tag = tag,
                    message = message
            ))
        }
    }

    override fun streamLogs(): Flow<List<LogEntry>> = logDao.getLogStream().map { logs ->
        logs.map { it.toModel() }
    }.asFlow()

    override suspend fun searchLogs(query: String): List<LogEntry> =
            logDao.searchLogs(query, query).map { it.toModel() }

    private fun LogEntity.toModel() = LogEntry(
            id = id,
            timestamp = timestamp,
            tag = tag,
            message = message
    )
}
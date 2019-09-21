package com.satis.app.common.logging

import com.satis.app.common.annotations.Io
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PersistedLogger @Inject constructor(
        private val logDao: LogDao,
        @Io private val io: CoroutineContext
) : Logger {

    override fun log(tag: String, message: String) {
        val timestamp = System.currentTimeMillis()
        GlobalScope.launch(io) {
            logDao.insertLog(LogEntity(
                    timestamp = timestamp,
                    tag = tag,
                    message = message
            ))
        }
    }

    override fun streamLogs(): Flow<List<LogEntry>> = logDao.getLogStream()
            .filterNotNull()
            .map { logs ->
                logs.map { it.toModel() }
            }
            .flowOn(io)

    override suspend fun searchLogs(query: String): List<LogEntry> {
        return withContext(io) {
            logDao.searchLogs(query, query).map { it.toModel() }
        }
    }

    private fun LogEntity.toModel() = LogEntry(
            id = id,
            timestamp = timestamp,
            tag = tag,
            message = message
    )
}
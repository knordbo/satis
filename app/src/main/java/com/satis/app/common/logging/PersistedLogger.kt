package com.satis.app.common.logging

import com.satis.app.common.annotations.Io
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class PersistedLoggerImpl @Inject constructor(
        private val logDao: LogDao,
        @Io private val io: CoroutineContext
) : PersistedLogger, CoroutineScope {

    override val coroutineContext: CoroutineContext = io

    override fun log(tag: String, message: String) {
        val timestamp = System.currentTimeMillis()
        launch(io) {
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
                logs.map(LogEntity::toModel)
            }
            .flowOn(io)

    override suspend fun searchLogs(query: String): List<LogEntry> {
        return withContext(io) {
            logDao.searchLogs(query, query).map { it.toModel() }
        }
    }

}

private fun LogEntity.toModel() = LogEntry(
        id = id,
        timestamp = timestamp,
        tag = tag,
        message = message
)

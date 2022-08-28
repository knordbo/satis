package com.satis.app.common.logging

import app.cash.sqldelight.coroutines.asFlow
import com.satis.app.common.annotations.Io
import com.satis.app.common.logging.db.LogEntity
import com.satis.app.common.logging.db.LogQueries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class PersistedLoggerImpl @Inject constructor(
  private val logQueries: LogQueries,
  @Io private val io: CoroutineContext,
) : PersistedLogger, CoroutineScope {

  override val coroutineContext: CoroutineContext = io

  override fun log(tag: String, message: String) {
    val timestamp = System.currentTimeMillis()
    launch(io) {
      logQueries.insertLog(
        timestamp = timestamp,
        tag = tag,
        message = message
      )
    }
  }

  override fun streamLogs(): Flow<List<LogEntry>> = logQueries.getLatestLogs()
    .asFlow()
    .map { logs ->
      logs.executeAsList().map(LogEntity::toModel)
    }
    .flowOn(io)

  override suspend fun searchLogs(query: String): List<LogEntry> {
    return withContext(io) {
      logQueries.searchLogs(query).executeAsList().map(LogEntity::toModel)
    }
  }

}

private fun LogEntity.toModel() = LogEntry(
  id = id,
  timestamp = timestamp,
  tag = tag,
  message = message
)

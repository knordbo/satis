package com.satis.app.common.logging

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.openSubscription

class PersistedLogger(
        private val logDao: LogDao,
        private val io: CoroutineDispatcher
) : Logger {

    override fun log(tag: String, message: String) {
        Log.d(tag, message)
        GlobalScope.launch(io) {
            logDao.insertLog(LogEntity(
                    timestamp = System.currentTimeMillis(),
                    tag = tag,
                    message = message
            ))
        }
    }

    override fun streamLogs(): ReceiveChannel<List<LogEntry>> = logDao.getLogStream().map { logs ->
        logs.map {
            LogEntry(it.id, it.timestamp, it.tag, it.message)
        }
    }.openSubscription()
}
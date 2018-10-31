package com.satis.app.common.logging

import android.util.Log
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class PersistedLogger(
        private val logDao: LogDao,
        private val backgroundDispatcher: CoroutineDispatcher
) : Logger {
    private val lock = ReentrantLock()

    override fun log(tag: String, message: String) {
        Log.d(tag, message)
        GlobalScope.launch(backgroundDispatcher) {
            lock.withLock {
                logDao.insertLog(LogEntity(
                        timestamp = System.currentTimeMillis(),
                        tag = tag,
                        message = message
                ))
            }
        }
    }

    override fun streamLogs(): Flowable<List<LogEntry>> = logDao.getLogStream().map { logs ->
        logs.map {
            LogEntry(it.id, it.timestamp, it.tag, it.message)
        }
    }
}
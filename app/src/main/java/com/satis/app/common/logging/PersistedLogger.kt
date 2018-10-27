package com.satis.app.common.logging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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

    override fun getLogs(): LiveData<List<LogEntry>> = Transformations.map(logDao.getLogStream()) { logs ->
        logs.map {
            LogEntry(it.id, it.timestamp, it.tag, it.message)
        }
    }
}
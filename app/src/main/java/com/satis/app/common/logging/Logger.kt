package com.satis.app.common.logging

import io.reactivex.Flowable

interface Logger {
    fun log(tag: String, message: String)
    fun streamLogs(): Flowable<List<LogEntry>>
}
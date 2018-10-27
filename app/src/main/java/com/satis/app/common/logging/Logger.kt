package com.satis.app.common.logging

import androidx.lifecycle.LiveData

interface Logger {
    fun log(tag: String, message: String)
    fun getLogs(): LiveData<List<LogEntry>>
}
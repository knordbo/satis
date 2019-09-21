package com.satis.app.startup

interface StartupTasks {
    suspend fun executeAll()
}
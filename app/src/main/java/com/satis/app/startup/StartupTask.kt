package com.satis.app.startup

interface StartupTask {
  suspend fun execute()
}
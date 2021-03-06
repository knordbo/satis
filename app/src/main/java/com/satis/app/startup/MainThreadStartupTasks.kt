package com.satis.app.startup

import com.satis.app.common.annotations.Main
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

class MainThreadStartupTasks @Inject constructor(
  @Main private val tasks: Provider<Set<@JvmSuppressWildcards StartupTask>>,
  @Main private val main: CoroutineContext
) : StartupTasks {
  override suspend fun executeAll() {
    withContext(main) {
      tasks.get().forEach { startupTask ->
        startupTask.execute()
      }
    }
  }
}
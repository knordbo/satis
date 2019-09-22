package com.satis.app.startup

import com.satis.app.common.annotations.Background
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

class BackgroundThreadStartupTasks @Inject constructor(
        @Background private val tasks: Provider<Set<@JvmSuppressWildcards StartupTask>>,
        @Background private val backgroundCoroutineContext: CoroutineContext
) : StartupTasks {
    override suspend fun executeAll() {
        withContext(backgroundCoroutineContext) {
            tasks.get().forEach(StartupTask::execute)
        }
    }
}

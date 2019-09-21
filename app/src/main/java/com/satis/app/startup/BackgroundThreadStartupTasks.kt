package com.satis.app.startup

import com.satis.app.common.annotations.Background
import com.satis.app.common.thread.offloadedDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class BackgroundThreadStartupTasks @Inject constructor(
        @Background private val tasks: Provider<Set<@JvmSuppressWildcards StartupTask>>
) : StartupTasks {
    override suspend fun executeAll() {
        withContext(offloadedDispatcher) {
            tasks.get().forEach(StartupTask::execute)
        }
    }
}

package com.satis.app.startup

import com.satis.app.common.annotations.Main
import com.satis.app.common.thread.assertMainThread
import com.satis.app.common.thread.mainDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class MainThreadStartupTasks @Inject constructor(
        @Main private val tasks: Provider<Set<@JvmSuppressWildcards StartupTask>>
) : StartupTasks {
    override suspend fun executeAll() {
        assertMainThread()
        withContext(mainDispatcher) {
            tasks.get().forEach(StartupTask::execute)
        }
    }
}
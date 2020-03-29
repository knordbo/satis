package com.satis.app

import android.app.Application
import androidx.work.Configuration
import com.satis.app.common.annotations.Background
import com.satis.app.common.annotations.Main
import com.satis.app.startup.StartupTasks
import com.satis.app.utils.context.ContextHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

class App @Inject constructor(
    private val workConfiguration: Provider<Configuration>,
    @Main override val coroutineContext: CoroutineContext,
    @Main private val mainThreadStartupTasks: StartupTasks,
    @Background private val backgroundThreadStartupTasks: StartupTasks
) : Application(), Configuration.Provider, CoroutineScope {

  override fun onCreate() {
    ContextHolder.context = applicationContext

    super.onCreate()

    launch {
      mainThreadStartupTasks.executeAll()
      backgroundThreadStartupTasks.executeAll()
    }
  }

  override fun getWorkManagerConfiguration(): Configuration = workConfiguration.get()

}

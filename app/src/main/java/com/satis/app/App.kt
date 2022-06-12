package com.satis.app

import android.app.Application
import androidx.work.Configuration
import com.satis.app.common.annotations.Background
import com.satis.app.common.annotations.Main
import com.satis.app.startup.StartupTasks
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

@HiltAndroidApp
class App : Application(), Configuration.Provider, CoroutineScope {

  @Inject lateinit var workConfiguration: Provider<Configuration>
  @Inject @Main lateinit var mainCoroutineContext: CoroutineContext
  @Inject @Main lateinit var mainThreadStartupTasks: StartupTasks
  @Inject @Background lateinit var backgroundThreadStartupTasks: StartupTasks

  override fun onCreate() {
    super.onCreate()

    launch {
      mainThreadStartupTasks.executeAll()
      backgroundThreadStartupTasks.executeAll()
    }
  }

  override fun getWorkManagerConfiguration(): Configuration = workConfiguration.get()

  override val coroutineContext: CoroutineContext
    get() = mainCoroutineContext

}

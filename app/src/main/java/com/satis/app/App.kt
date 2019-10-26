package com.satis.app

import androidx.work.Configuration
import com.satis.app.common.annotations.Background
import com.satis.app.common.annotations.Main
import com.satis.app.di.DaggerAppComponent
import com.satis.app.startup.StartupTasks
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

class App : DaggerApplication(), Configuration.Provider, CoroutineScope {

    @Inject lateinit var workConfiguration: Provider<Configuration>
    @Inject @Main override lateinit var coroutineContext: CoroutineContext
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

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.factory().create(this)
}
package com.satis.app

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import com.satis.app.common.annotations.Background
import com.satis.app.common.annotations.Main
import com.satis.app.di.DaggerAppComponent
import com.satis.app.startup.StartupTasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

class App : Application(), Configuration.Provider, CoroutineScope {

    @Inject lateinit var workConfiguration: Provider<Configuration>
    @Inject @Main override lateinit var coroutineContext: CoroutineContext
    @Inject @Main lateinit var mainThreadStartupTasks: StartupTasks
    @Inject @Background lateinit var backgroundThreadStartupTasks: StartupTasks

    val appComponent by lazy { DaggerAppComponent.factory().create(this) }

    override fun onCreate() {
        appComponent.inject(this)

        super.onCreate()

        launch {
            mainThreadStartupTasks.executeAll()
            backgroundThreadStartupTasks.executeAll()
        }
    }

    override fun getWorkManagerConfiguration(): Configuration = workConfiguration.get()

}

val Context.appComponent get() = (applicationContext as App).appComponent
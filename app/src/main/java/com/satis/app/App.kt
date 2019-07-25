package com.satis.app

import androidx.work.Configuration
import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.apply
import com.satis.app.di.DaggerAppComponent
import com.satis.app.work.WorkScheduler
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

class App : DaggerApplication(), Configuration.Provider, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main.immediate

    @Inject lateinit var prefs: Prefs
    @Inject lateinit var workScheduler: Provider<WorkScheduler>
    @Inject lateinit var workConfiguration: Provider<Configuration>

    private val offloadedDispatcher: CoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    override fun onCreate() {
        super.onCreate()

        launch {
            blockingCalls()

            withContext(offloadedDispatcher) {
                offloadedCalls()
            }
        }
    }

    /**
     * Blocking calls that we need to do in the startup call stack
     */
    private fun blockingCalls() {
        // Apply the current theme
        prefs.theme.apply()

        configureBuildVariantFunctionality(this)
    }

    /**
     * Things we want executed but are not needed in the startup call stack
     */
    private fun offloadedCalls() {
        workScheduler.get().schedule()
    }

    override fun getWorkManagerConfiguration(): Configuration = workConfiguration.get()

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.factory().create(this)
}
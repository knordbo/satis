package com.satis.app

import android.app.Application
import androidx.work.Configuration
import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.apply
import com.satis.app.feature.account.accountModule
import com.satis.app.feature.cards.cardModule
import com.satis.app.feature.images.imagesModule
import com.satis.app.feature.playground.playgroundModule
import com.satis.app.work.WorkScheduler
import com.satis.app.work.workerModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class App : Application(), Configuration.Provider, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main.immediate

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
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@App)

            modules(
                    appModule,
                    workerModule,
                    // features
                    cardModule,
                    accountModule,
                    imagesModule,
                    playgroundModule
            )
        }

        // Apply the current theme
        get<Prefs>().theme.apply()
    }

    /**
     * Things we want executed but are not needed in the startup call stack
     */
    private fun offloadedCalls() {
        get<WorkScheduler>().schedule()
    }

    override fun getWorkManagerConfiguration(): Configuration = get()
}
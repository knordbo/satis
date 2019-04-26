package com.satis.app

import android.app.Application
import androidx.work.Configuration
import com.satis.app.feature.account.accountModule
import com.satis.app.feature.cards.cardModule
import com.satis.app.feature.images.imagesModule
import com.satis.app.feature.playground.playgroundModule
import com.satis.app.work.WorkScheduler
import com.satis.app.work.workerModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class App : Application(), Configuration.Provider {

    private val offloadedExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler {
            // ignore
        }

        startKoin(this, listOf(
                appModule,
                workerModule,
                // features
                cardModule,
                accountModule,
                imagesModule,
                playgroundModule
        ))

        // Things we want executed but are not needed in the startup call stack
        offloadedExecutor.execute {
            get<WorkScheduler>().schedule()
        }
    }

    override fun getWorkManagerConfiguration(): Configuration = get()
}
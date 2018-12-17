package com.satis.app

import android.app.Application
import androidx.work.WorkManager
import com.satis.app.feature.account.accountModule
import com.satis.app.feature.cards.cardModule
import com.satis.app.feature.images.imagesModule
import com.satis.app.feature.playground.playgroundModule
import com.satis.app.work.WorkScheduler
import com.satis.app.work.workerModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        configureVariant()

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

        WorkManager.initialize(this, get())

        get<WorkScheduler>().schedule()
    }

}
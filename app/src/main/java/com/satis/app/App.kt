package com.satis.app

import android.app.Application
import androidx.work.WorkManager
import com.satis.app.feature.account.accountModule
import com.satis.app.feature.cards.cardModule
import com.satis.app.work.WorkScheduler
import com.satis.app.work.workerModule
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        configureVariant()

        startKoin(this, listOf(
                appModule,
                cardModule,
                workerModule,
                accountModule
        ))

        WorkManager.initialize(this, get())

        get<WorkScheduler>().schedule()
    }

}
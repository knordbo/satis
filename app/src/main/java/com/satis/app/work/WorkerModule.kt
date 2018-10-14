package com.satis.app.work

import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.satis.app.IO
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module

val workerModule = module {
    single<WorkManager> {
        WorkManager.getInstance()
    }

    single<WorkerFactory> {
        object : WorkerFactory() {
            override fun createWorker(
                    appContext: Context,
                    workerClassName: String,
                    workerParameters: WorkerParameters
            ): ListenableWorker? = get(workerClassName) { parametersOf(appContext, workerParameters) }
        }
    }

    single<Configuration> {
        Configuration.Builder().setWorkerFactory(get()).build()
    }

    single<WorkScheduler> {
        WorkScheduler(get(), get())
    }

    factory<ListenableWorker>(NetworkWorker::class.java.name) { (context: Context, workerParameters: WorkerParameters) ->
        NetworkWorker(context, workerParameters, get(IO), get())
    }

    factory<ListenableWorker>(ChargingNetworkWorker::class.java.name) { (context: Context, workerParameters: WorkerParameters) ->
        ChargingNetworkWorker(context, workerParameters, get(IO), get())
    }
}
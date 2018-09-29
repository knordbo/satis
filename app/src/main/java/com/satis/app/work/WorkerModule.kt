package com.satis.app.work

import android.content.Context
import androidx.work.Configuration
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module

val workerModule = module {
    single<WorkerFactory> {
        WorkerFactory { appContext, workerClassName, workerParameters ->
            get(workerClassName) { parametersOf(appContext, workerParameters) }
        }
    }

    single<Configuration> {
        Configuration.Builder().setWorkerFactory(get()).build()
    }

    single<WorkScheduler> {
        WorkScheduler(get())
    }

    factory<Worker>(NetworkWorker::class.java.name) { (context: Context, workerParameters: WorkerParameters) ->
        NetworkWorker(context, workerParameters, get())
    }

    factory<Worker>(ChargingNetworkWorker::class.java.name) { (context: Context, workerParameters: WorkerParameters) ->
        ChargingNetworkWorker(context, workerParameters, get())
    }
}
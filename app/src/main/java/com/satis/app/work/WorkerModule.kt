package com.satis.app.work

import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.satis.app.Io
import com.satis.app.utils.system.forNameAsSubclass
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val workerModule = module {
    single<WorkManager> {
        WorkManager.getInstance(get())
    }

    single<WorkerFactory> {
        object : WorkerFactory() {
            override fun createWorker(
                    appContext: Context,
                    workerClassName: String,
                    workerParameters: WorkerParameters
            ): ListenableWorker? {
                val kClass = forNameAsSubclass<ListenableWorker>(workerClassName)?.kotlin
                return if (kClass != null) {
                    get(clazz = kClass, qualifier = null) { parametersOf(appContext, workerParameters) }
                } else {
                    null
                }
            }
        }
    }

    single<Configuration> {
        Configuration.Builder().setWorkerFactory(get()).build()
    }

    single<WorkScheduler> {
        WorkScheduler(get(), get())
    }

    factory<NetworkWorker> { (context: Context, workerParameters: WorkerParameters) ->
        NetworkWorker(context, workerParameters, get(named<Io>()), get())
    }

    factory<ChargingNetworkWorker> { (context: Context, workerParameters: WorkerParameters) ->
        ChargingNetworkWorker(context, workerParameters, get(named<Io>()), get())
    }
}
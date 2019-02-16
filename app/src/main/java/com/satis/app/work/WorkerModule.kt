package com.satis.app.work

import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.satis.app.IO
import com.satis.app.utils.system.forNameAsSubclass
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module
import org.koin.experimental.builder.getForClass

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
            ): ListenableWorker? = getForClass(clazz = forNameAsSubclass(workerClassName)) { parametersOf(appContext, workerParameters) }
        }
    }

    single<Configuration> {
        Configuration.Builder().setWorkerFactory(get()).build()
    }

    single<WorkScheduler> {
        WorkScheduler(get(), get())
    }

    factory<NetworkWorker> { (context: Context, workerParameters: WorkerParameters) ->
        NetworkWorker(context, workerParameters, get(IO), get())
    }

    factory<ChargingNetworkWorker> { (context: Context, workerParameters: WorkerParameters) ->
        ChargingNetworkWorker(context, workerParameters, get(IO), get())
    }
}
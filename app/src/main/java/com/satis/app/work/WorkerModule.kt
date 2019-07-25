package com.satis.app.work

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [WorkerBindingModule::class])
class WorkerModule {

    @Provides
    @Singleton
    fun provideWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideConfiguration(workerFactory: WorkerFactory): Configuration =
            Configuration
                    .Builder()
                    .setWorkerFactory(workerFactory)
                    .build()

}

@Module
abstract class WorkerBindingModule {

    @Binds
    abstract fun provideWorkerFactory(bind: InjectingWorkerFactory): WorkerFactory

    @Binds
    @Singleton
    abstract fun provideWorkScheduler(bind: WorkSchedulerImpl): WorkScheduler

    @Binds
    @IntoMap
    @WorkerKey(NetworkWorker::class)
    abstract fun provideNetworkWorker(bind: NetworkWorker.Factory): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(ChargingNetworkWorker::class)
    abstract fun provideChargingNetworkWorker(bind: ChargingNetworkWorker.Factory): ChildWorkerFactory

}
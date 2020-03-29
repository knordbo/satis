package com.satis.app.work

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [WorkerBindingModule::class])
object WorkerModule {

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
  abstract fun provideWorkScheduler(bind: WorkSchedulerImpl): WorkScheduler

}
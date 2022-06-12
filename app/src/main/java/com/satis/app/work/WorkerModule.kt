package com.satis.app.work

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WorkerModule {

  @Provides
  @Singleton
  fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
    WorkManager.getInstance(context)

  @Provides
  @Singleton
  fun provideConfiguration(workerFactory: HiltWorkerFactory): Configuration =
    Configuration
      .Builder()
      .setWorkerFactory(workerFactory)
      .build()

}

@InstallIn(SingletonComponent::class)
@Module
abstract class WorkerBindingModule {

  @Binds
  abstract fun provideWorkScheduler(bind: WorkSchedulerImpl): WorkScheduler

}
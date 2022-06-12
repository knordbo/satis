package com.satis.app.common.logging

import com.satis.app.Database
import com.satis.app.common.logging.db.LogQueries
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LogModule {
  @Provides
  @Singleton
  fun provideLogQueries(database: Database): LogQueries = database.logQueries
}

@InstallIn(SingletonComponent::class)
@Module
abstract class LogBindingModule {
  @Binds
  abstract fun providePersistedLogger(bind: PersistedLoggerImpl): PersistedLogger

  @Binds
  abstract fun provideDelegatingLogger(bind: DelegatingLoggerImpl): Logger

  @Binds
  @IntoSet
  abstract fun providePersistedLoggerForSet(bind: PersistedLoggerImpl): Logger

  @Binds
  @IntoSet
  abstract fun provideAndroidLoggerForSet(bind: AndroidLoggerImpl): Logger
}
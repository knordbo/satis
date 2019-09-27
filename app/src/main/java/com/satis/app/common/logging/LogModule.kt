package com.satis.app.common.logging

import com.satis.app.common.db.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module(includes = [LogBindingModule::class])
class LogModule {
    @Provides
    @Singleton
    fun provideLogDao(appDatabase: AppDatabase): LogDao = appDatabase.logDao()
}

@Module
abstract class LogBindingModule {
    @Binds
    abstract fun providePersistedLogger(bind: PersistedLoggerImpl): PersistedLogger

    @Binds
    abstract fun provideDispatchingLogger(bind: DispatchingLoggerImpl): Logger

    @Binds
    @IntoSet
    abstract fun providePersistedLoggerForSet(bind: PersistedLoggerImpl): Logger

    @Binds
    @IntoSet
    abstract fun provideAndroidLoggerForSet(bind: AndroidLoggerImpl): Logger
}
package com.satis.app.di

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import coil.ImageLoader
import coil.imageLoader
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.satis.app.Database
import com.satis.app.common.annotations.Background
import com.satis.app.common.annotations.DatabaseName
import com.satis.app.common.annotations.Io
import com.satis.app.common.annotations.Main
import com.satis.app.common.annotations.SharedPrefsName
import com.satis.app.common.annotations.WorkerIo
import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.PrefsImpl
import com.satis.app.common.updater.ImmediateAppUpdater
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

  @Provides
  @Singleton
  @SharedPrefsName
  fun provideSharedPrefsName(): String = "satis.shared.prefs"

  @Provides
  @Singleton
  @DatabaseName
  fun provideDatabaseName(): String = "satis.sqldelight.db"

  @Provides
  @Singleton
  fun provideDatabase(
    @ApplicationContext context: Context,
    @DatabaseName databaseName: String,
  ): Database =
    Database(
      driver = AndroidSqliteDriver(
        schema = Database.Schema,
        context = context,
        name = databaseName,
        useNoBackupDirectory = true
      ),
    )

  @Provides
  @Singleton
  @Main
  fun provideMainCoroutineContext(): CoroutineDispatcher = Dispatchers.Main.immediate

  @Provides
  @Singleton
  @Background
  fun provideBackgroundCoroutineContext(): CoroutineDispatcher = Dispatchers.Default

  @Provides
  @Singleton
  @Io
  fun provideIoCoroutineContext(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @Singleton
  @WorkerIo
  fun provideWorkerIoCoroutineContext(@Io dispatcher: CoroutineDispatcher): CoroutineDispatcher =
    dispatcher.limitedParallelism(parallelism = 1)

  @Provides
  @Singleton
  fun provideAppUpdateManager(@ApplicationContext context: Context): AppUpdateManager =
    AppUpdateManagerFactory.create(context)

  @Provides
  @Singleton
  fun provideNotificationManager(@ApplicationContext context: Context): NotificationManagerCompat =
    NotificationManagerCompat.from(context)

  @Provides
  @Singleton
  fun provideImageLoader(@ApplicationContext context: Context): ImageLoader = context.imageLoader
}

@InstallIn(SingletonComponent::class)
@Module
abstract class AppBindingModule {

  @Binds
  abstract fun providePrefs(bind: PrefsImpl): Prefs

  @Binds
  abstract fun provideImmediateAppUpdaterFactory(bind: ImmediateAppUpdater.FactoryImpl):
    ImmediateAppUpdater.Factory

  @Binds
  @Singleton
  @Main
  abstract fun provideMainCoroutineContext(@Main bind: CoroutineDispatcher): CoroutineContext

  @Binds
  @Singleton
  @Background
  abstract fun provideBackgroundCoroutineContext(@Background bind: CoroutineDispatcher):
    CoroutineContext

  @Binds
  @Singleton
  @Io
  abstract fun provideIoCoroutineContext(@Io bind: CoroutineDispatcher): CoroutineContext

  @Binds
  @Singleton
  @WorkerIo
  abstract fun provideWorkerIoCoroutineContext(@WorkerIo bind: CoroutineDispatcher):
    CoroutineContext

}

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
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
  fun provideMainCoroutineContext(): CoroutineContext = Dispatchers.Main.immediate

  @Provides
  @Singleton
  @Background
  fun provideBackgroundCoroutineContext(): CoroutineContext = Dispatchers.Default

  @Provides
  @Singleton
  @Io
  fun provideIoCoroutineContext(): CoroutineContext = Dispatchers.IO

  @Provides
  @Singleton
  @WorkerIo
  fun provideWorkerIoCoroutineContext(): CoroutineContext =
    Executors.newSingleThreadExecutor().asCoroutineDispatcher()

  @Provides
  @Singleton
  fun provideAppUpdateManager(@ApplicationContext context: Context): AppUpdateManager =
    AppUpdateManagerFactory.create(context)

  @Provides
  @Singleton
  fun provideUserId(prefs: Prefs) = prefs.userId

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
  abstract fun provideImmediateAppUpdaterFactory(bind: ImmediateAppUpdater.FactoryImpl): ImmediateAppUpdater.Factory

}

package com.satis.app.feature.notifications

import com.satis.app.AccountDatabase
import com.satis.app.common.account.AccountId
import com.satis.app.di.account.AccountScope
import com.satis.app.di.account.NotificationRepositoryProvider
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.data.NotificationRepositoryImpl
import com.satis.app.feature.notifications.data.db.NotificationQueries
import com.satis.app.feature.notifications.system.NotificationChannelHelper
import com.satis.app.feature.notifications.system.NotificationChannelHelperImpl
import com.satis.app.feature.notifications.system.PushNotificationHandler
import com.satis.app.feature.notifications.system.PushNotificationHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck

@Module(includes = [NotificationAccountBindingModule::class])
@DisableInstallInCheck
object NotificationAccountModule {
  @Provides
  fun provideNotificationQueries(database: AccountDatabase): NotificationQueries =
    database.notificationQueries
}

@Module
@DisableInstallInCheck
abstract class NotificationAccountBindingModule {

  @Binds
  @AccountScope
  abstract fun provideNotificationRepository(bind: NotificationRepositoryImpl): NotificationRepository

  @Binds
  @AccountScope
  abstract fun providePushNotificationHandler(bind: PushNotificationHandlerImpl): PushNotificationHandler
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationSingletonModule {

  @Binds
  abstract fun provideNotificationChannelHelper(bind: NotificationChannelHelperImpl): NotificationChannelHelper
}

@Module
@InstallIn(ViewModelComponent::class)
object NotificationViewModelModule {
  @Provides
  @ViewModelScoped
  fun provideNotificationRepository(
    accountId: AccountId,
    notificationRepositoryProvider: NotificationRepositoryProvider,
  ): NotificationRepository = notificationRepositoryProvider.get(accountId)
}
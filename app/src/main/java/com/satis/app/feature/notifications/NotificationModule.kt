package com.satis.app.feature.notifications

import com.satis.app.Database
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
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotificationModule {
  @Provides
  @Singleton
  fun provideNotificationQueries(database: Database): NotificationQueries =
    database.notificationQueries
}

@InstallIn(SingletonComponent::class)
@Module
abstract class NotificationBindingModule {

  @Binds
  abstract fun provideNotificationRepository(bind: NotificationRepositoryImpl): NotificationRepository

  @Binds
  abstract fun provideNotificationChannelHelper(bind: NotificationChannelHelperImpl): NotificationChannelHelper

  @Binds
  abstract fun providePushNotificationHandler(bind: PushNotificationHandlerImpl): PushNotificationHandler
}

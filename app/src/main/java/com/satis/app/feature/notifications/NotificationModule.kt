package com.satis.app.feature.notifications

import android.app.Service
import com.satis.app.common.service.ServiceKey
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.data.NotificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [NotificationBindingModule::class])
object NotificationModule

@Module
abstract class NotificationBindingModule {

  @Binds
  @IntoMap
  @ServiceKey(NotificationService::class)
  abstract fun provideNotificationService(bind: NotificationService): Service

  @Binds
  abstract fun provideNotificationRepository(bind: NotificationRepositoryImpl): NotificationRepository

  @Binds
  abstract fun provideNotificationChannelHelper(bind: NotificationChannelHelperImpl): NotificationChannelHelper

  @Binds
  abstract fun providePushNotificationHandler(bind: PushNotificationHandlerImpl): PushNotificationHandler

}

package com.satis.app.feature.notifications

import android.app.Service
import androidx.fragment.app.Fragment
import com.satis.app.Database
import com.satis.app.common.fragment.FragmentKey
import com.satis.app.common.service.ServiceKey
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.data.NotificationRepositoryImpl
import com.satis.app.feature.notifications.data.db.NotificationQueries
import com.satis.app.feature.notifications.system.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [NotificationBindingModule::class])
object NotificationModule {
  @Provides
  @Singleton
  fun provideNotificationQueries(database: Database): NotificationQueries = database.notificationQueries
}

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

  @Binds
  @IntoMap
  @FragmentKey(NotificationFragment::class)
  abstract fun provideNotificationFragment(bind: NotificationFragment): Fragment
}

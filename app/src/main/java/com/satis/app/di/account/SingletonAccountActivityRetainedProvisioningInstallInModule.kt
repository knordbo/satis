package com.satis.app.di.account

import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.system.PushNotificationHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object SingletonAccountActivityRetainedProvisioningInstallInModule {

  @Provides
  fun provideCardRepository(component: SingletonAccountComponent): CardRepository {
    return component.cardRepository()
  }

  @Provides
  fun provideUnsplashRepository(component: SingletonAccountComponent): UnsplashRepository {
    return component.unsplashRepository()
  }

  @Provides
  fun provideNotificationRepository(component: SingletonAccountComponent): NotificationRepository {
    return component.notificationRepository()
  }

  @Provides
  fun providePushNotificationHandler(component: SingletonAccountComponent): PushNotificationHandler {
    return component.pushNotificationHandler()
  }

}

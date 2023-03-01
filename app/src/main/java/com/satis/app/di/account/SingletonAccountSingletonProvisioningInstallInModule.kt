package com.satis.app.di.account

import com.satis.app.common.account.AccountId
import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.system.PushNotificationHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SingletonAccountSingletonProvisioningInstallInModule {

  @Provides
  fun provideCardRepository(provider: SingletonAccountComponentProvider): AccountProvider<CardRepository> {
    return provider.provide(SingletonAccountComponent::cardRepository)
  }

  @Provides
  fun provideUnsplashRepository(provider: SingletonAccountComponentProvider): AccountProvider<UnsplashRepository> {
    return provider.provide(SingletonAccountComponent::unsplashRepository)
  }

  @Provides
  fun provideNotificationRepository(provider: SingletonAccountComponentProvider): AccountProvider<NotificationRepository> {
    return provider.provide(SingletonAccountComponent::notificationRepository)
  }

  @Provides
  fun providePushNotificationHandler(provider: SingletonAccountComponentProvider): AccountProvider<PushNotificationHandler> {
    return provider.provide(SingletonAccountComponent::pushNotificationHandler)
  }

}

inline fun <T> SingletonAccountComponentProvider.provide(
  crossinline singletonAccountComponentProvider: (SingletonAccountComponent) -> T,
): AccountProvider<T> = object : AccountProvider<T> {
  override fun get(accountId: AccountId): T =
    singletonAccountComponentProvider(this@provide.get(accountId))
}

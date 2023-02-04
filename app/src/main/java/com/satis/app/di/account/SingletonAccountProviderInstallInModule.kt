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
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(subcomponents = [
  SingletonAccountComponent::class
])
object SingletonAccountProviderInstallInModule {

  @Provides
  fun provideCardRepositoryProvider(accountComponentHolder: AccountComponentHolder): CardRepositoryProvider {
    return object : CardRepositoryProvider {
      override fun get(accountId: AccountId): CardRepository =
        accountComponentHolder.get(accountId).cardRepository()
    }
  }

  @Provides
  fun provideUnsplashRepositoryProvider(accountComponentHolder: AccountComponentHolder): UnsplashRepositoryProvider {
    return object : UnsplashRepositoryProvider {
      override fun get(accountId: AccountId): UnsplashRepository =
        accountComponentHolder.get(accountId).unsplashRepository()
    }
  }

  @Provides
  fun provideNotificationRepositoryProvider(accountComponentHolder: AccountComponentHolder): NotificationRepositoryProvider {
    return object : NotificationRepositoryProvider {
      override fun get(accountId: AccountId): NotificationRepository =
        accountComponentHolder.get(accountId).notificationRepository()
    }
  }

  @Provides
  fun providePushNotificationHandlerProvider(accountComponentHolder: AccountComponentHolder): PushNotificationHandlerProvider {
    return object : PushNotificationHandlerProvider {
      override fun get(accountId: AccountId): PushNotificationHandler =
        accountComponentHolder.get(accountId).pushNotificationHandler()
    }
  }
}

interface CardRepositoryProvider {
  fun get(accountId: AccountId): CardRepository
}

interface UnsplashRepositoryProvider {
  fun get(accountId: AccountId): UnsplashRepository
}

interface NotificationRepositoryProvider {
  fun get(accountId: AccountId): NotificationRepository
}

interface PushNotificationHandlerProvider {
  fun get(accountId: AccountId): PushNotificationHandler
}

@Singleton
class AccountComponentHolder @Inject constructor(
  private val singletonAccountComponentBuilder: SingletonAccountComponent.Builder,
) {

  private val map = ConcurrentHashMap<AccountId, SingletonAccountComponent>()

  internal fun get(accountId: AccountId): SingletonAccountComponent {
    return map.computeIfAbsent(accountId) {
      singletonAccountComponentBuilder.accountId(it).build()
    }
  }
}

package com.satis.app.di.account

import com.satis.app.common.account.AccountId
import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.system.PushNotificationHandler
import dagger.BindsInstance
import dagger.Subcomponent

@AccountScope
@Subcomponent(modules = [
  SingletonAccountModule::class
])
abstract class SingletonAccountComponent {

  abstract fun cardRepository(): CardRepository

  abstract fun unsplashRepository(): UnsplashRepository

  abstract fun notificationRepository(): NotificationRepository

  abstract fun pushNotificationHandler(): PushNotificationHandler

  @Subcomponent.Builder
  interface Builder {
    @BindsInstance
    fun accountId(accountId: AccountId): Builder

    fun build(): SingletonAccountComponent
  }
}

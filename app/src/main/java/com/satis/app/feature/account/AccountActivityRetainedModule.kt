package com.satis.app.feature.account

import com.satis.app.common.account.AccountId
import com.satis.app.di.account.SingletonAccountComponent
import com.satis.app.di.account.SingletonAccountComponentProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AccountActivityRetainedModule {
  @Provides
  fun provideAccountId(accountHolder: AccountHolder): AccountId = accountHolder.requireAccountId()

  @Provides
  fun provideSingletonAccountComponent(
    accountId: AccountId,
    singletonAccountComponentProvider: SingletonAccountComponentProvider,
  ): SingletonAccountComponent = singletonAccountComponentProvider.get(accountId)
}

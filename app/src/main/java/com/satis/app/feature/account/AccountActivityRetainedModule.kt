package com.satis.app.feature.account

import com.satis.app.common.prefs.AccountId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AccountActivityRetainedModule {
  @Provides
  fun provideAccountId(accountHolder: AccountHolder): AccountId = accountHolder.requireAccountId()
}

package com.satis.app.feature.account

import com.satis.app.feature.account.appinfo.AppInfoRetriever
import com.satis.app.feature.account.appinfo.AppInfoRetrieverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AccountModule {

  @Binds
  abstract fun provideAppInfoRetriever(bind: AppInfoRetrieverImpl): AppInfoRetriever

}
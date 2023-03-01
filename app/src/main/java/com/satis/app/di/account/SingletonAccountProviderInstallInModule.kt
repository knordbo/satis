package com.satis.app.di.account

import com.satis.app.common.account.AccountId
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
  fun provideSingletonAccountComponent(accountComponentHolder: AccountComponentHolder): SingletonAccountComponentProvider {
    return accountComponentHolder
  }
}

interface SingletonAccountComponentProvider {
  fun get(accountId: AccountId): SingletonAccountComponent
}

@Singleton
class AccountComponentHolder @Inject constructor(
  private val singletonAccountComponentBuilder: SingletonAccountComponent.Builder,
) : SingletonAccountComponentProvider {

  private val map = ConcurrentHashMap<AccountId, SingletonAccountComponent>()

  override fun get(accountId: AccountId): SingletonAccountComponent {
    return map.computeIfAbsent(accountId) {
      singletonAccountComponentBuilder.accountId(it).build()
    }
  }
}

package com.satis.app.di.account

import com.satis.app.common.account.AccountId
import com.satis.app.feature.cards.data.CardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(subcomponents = [
  SingletonPerAccountComponent::class
])
object SingletonAccountModule {
  @Singleton
  @Provides
  fun provideCardRepositoryProvider(accountComponentHolder: AccountComponentHolder): CardRepositoryProvider {
    return accountComponentHolder
  }
}

interface CardRepositoryProvider {
  fun cardRepository(accountId: AccountId): CardRepository
}

@Singleton
class AccountComponentHolder @Inject constructor(
  private val singletonPerAccountComponentBuilder: SingletonPerAccountComponent.Builder,
) : CardRepositoryProvider {

  private val map = mutableMapOf<AccountId, SingletonPerAccountComponent>()

  @Synchronized
  private fun get(accountId: AccountId): SingletonPerAccountComponent {
    return map.computeIfAbsent(accountId) {
      singletonPerAccountComponentBuilder.accountId(it).build()
    }
  }

  override fun cardRepository(accountId: AccountId): CardRepository =
    get(accountId).cardRepository()
}

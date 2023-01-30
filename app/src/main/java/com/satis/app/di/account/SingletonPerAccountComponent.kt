package com.satis.app.di.account

import com.satis.app.common.account.AccountId
import com.satis.app.feature.cards.data.CardRepository
import dagger.BindsInstance
import dagger.Subcomponent

@AccountScope
@Subcomponent(modules = [
  SingletonPerAccountModule::class
])
abstract class SingletonPerAccountComponent {

  abstract fun cardRepository(): CardRepository

  @Subcomponent.Builder
  interface Builder {
    @BindsInstance
    fun accountId(accountId: AccountId): Builder

    fun build(): SingletonPerAccountComponent
  }
}

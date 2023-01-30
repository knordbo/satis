package com.satis.app.di.account

import com.satis.app.common.account.AccountId
import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.feature.cards.data.CardRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck

@DisableInstallInCheck
@Module
object SingletonPerAccountModule {

  @AccountScope
  @Provides
  fun provideCardRepository(
    accountId: AccountId,
    cardRepositoryFactory: CardRepositoryImpl.Factory,
  ): CardRepository = cardRepositoryFactory.create(accountId)

}

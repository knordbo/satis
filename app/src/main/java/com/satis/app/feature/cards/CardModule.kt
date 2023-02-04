package com.satis.app.feature.cards

import com.google.firebase.firestore.FirebaseFirestore
import com.satis.app.common.account.AccountId
import com.satis.app.di.account.AccountScope
import com.satis.app.di.account.CardRepositoryProvider
import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.feature.cards.data.CardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module
@DisableInstallInCheck
abstract class CardAccountModule {

  @Binds
  @AccountScope
  abstract fun provideCardRepository(bind: CardRepositoryImpl): CardRepository
}

@Module
@InstallIn(SingletonComponent::class)
object CardModule {

  @Provides
  @Singleton
  fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

}

@Module
@InstallIn(ViewModelComponent::class)
object CardViewModelModule {

  @Provides
  @ViewModelScoped
  fun provideCardRepository(
    accountId: AccountId,
    cardRepositoryProvider: CardRepositoryProvider,
  ): CardRepository {
    return cardRepositoryProvider.get(accountId)
  }

}

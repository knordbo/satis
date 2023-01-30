package com.satis.app.feature.cards

import com.google.firebase.firestore.FirebaseFirestore
import com.satis.app.common.account.AccountId
import com.satis.app.di.account.CardRepositoryProvider
import com.satis.app.feature.cards.data.CardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CardModule {

  @Singleton
  @Provides
  fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

}

@InstallIn(ViewModelComponent::class)
@Module
object CardViewModelModule {

  @ViewModelScoped
  @Provides
  fun provideCardRepository(
    accountId: AccountId,
    cardRepositoryProvider: CardRepositoryProvider,
  ): CardRepository {
    return cardRepositoryProvider.cardRepository(accountId)
  }

}

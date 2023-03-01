package com.satis.app.feature.cards

import com.google.firebase.firestore.FirebaseFirestore
import com.satis.app.di.account.AccountScope
import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.feature.cards.data.CardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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


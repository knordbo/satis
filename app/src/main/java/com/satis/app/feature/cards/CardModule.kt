package com.satis.app.feature.cards

import com.google.firebase.firestore.FirebaseFirestore
import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.feature.cards.data.CardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
abstract class CardBindingModule {

  @Binds
  abstract fun provideCardRepository(bind: CardRepositoryImpl): CardRepository

}
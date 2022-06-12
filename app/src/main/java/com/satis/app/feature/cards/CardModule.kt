package com.satis.app.feature.cards

import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.satis.app.common.fragment.FragmentKey
import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.feature.cards.data.CardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CardModule {

  @Singleton
  @Provides
  fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

}

@InstallIn(SingletonComponent::class)
@Module
abstract class CardBindingModule {

  @Binds
  @IntoMap
  @FragmentKey(CardFragment::class)
  abstract fun provideCardFragment(bind: CardFragment): Fragment

  @Binds
  abstract fun provideCardRespository(bind: CardRepositoryImpl): CardRepository

  @Binds
  abstract fun provideCardViewModelFactory(bind: CardViewModel.FactoryImpl): CardViewModel.Factory

}
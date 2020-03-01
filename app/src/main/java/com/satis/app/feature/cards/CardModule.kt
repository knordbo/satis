package com.satis.app.feature.cards

import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.satis.app.common.fragment.FragmentKey
import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.feature.cards.data.CardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [CardBindingModule::class])
object CardModule {

  @Singleton
  @Provides
  fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

}

@Module
abstract class CardBindingModule {

  @Binds
  @IntoMap
  @FragmentKey(CardFragment::class)
  abstract fun provideCardFragment(bind: CardFragment): Fragment

  @Binds
  abstract fun provideCardRespository(bind: CardRepositoryImpl): CardRepository

}
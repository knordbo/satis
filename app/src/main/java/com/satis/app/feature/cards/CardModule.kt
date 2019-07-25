package com.satis.app.feature.cards

import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.satis.app.common.fragment.FragmentKey
import com.satis.app.common.prefs.Prefs
import com.satis.app.feature.cards.data.CardProvider
import com.satis.app.feature.cards.data.DefaultCardProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [CardBindingModule::class])
class CardModule {

    @Singleton
    @Provides
    fun provideCardProvider(prefs: Prefs): CardProvider {
        return DefaultCardProvider(prefs.uniqueId, FirebaseFirestore.getInstance())
    }

}

@Module
abstract class CardBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(CardFragment::class)
    abstract fun provideCardFragment(bind: CardFragment): Fragment

}
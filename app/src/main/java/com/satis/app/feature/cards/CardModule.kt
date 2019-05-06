package com.satis.app.feature.cards

import com.google.firebase.firestore.FirebaseFirestore
import com.satis.app.common.prefs.Prefs
import com.satis.app.feature.cards.data.CardProvider
import com.satis.app.feature.cards.data.DefaultCardProvider
import org.koin.dsl.module

val cardModule = module {
    single<CardProvider> {
        DefaultCardProvider(get<Prefs>().uniqueId, FirebaseFirestore.getInstance())
    }

    factory<CardFragment> {
        CardFragment()
    }

    factory<CardViewModel> { (initialState: CardState) ->
        CardViewModel(initialState, get())
    }
}
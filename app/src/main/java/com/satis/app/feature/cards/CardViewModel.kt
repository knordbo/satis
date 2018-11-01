package com.satis.app.feature.cards

import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.satis.app.BuildConfig
import com.satis.app.feature.cards.data.Card
import com.satis.app.feature.cards.data.CardProvider
import com.satis.app.utils.coroutines.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class CardViewModel(
        initialSate: CardState,
        private val cardProvider: CardProvider
) : BaseViewModel<CardState>(
        initialState = initialSate,
        debugMode = BuildConfig.DEBUG
) {
    init {
        logStateChanges()
        getCards()
    }

    fun addCard(card: Card) {
        cardProvider.addCard(card)
    }

    fun like(id: String, like: Boolean) {
        cardProvider.like(id, like)
    }

    fun dislike(id: String, dislike: Boolean) {
        cardProvider.dislike(id, dislike)
    }

    fun removeCard(id: String) {
        cardProvider.removeCard(id)
    }

    private fun getCards() {
        launch {
            for (cards in cardProvider.getCards()) {
                setState {
                    copy(cards = cards.sortedByDescending { it.likes })
                }
            }
        }
    }

    companion object : MvRxViewModelFactory<CardState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: CardState): BaseMvRxViewModel<CardState> =
                activity.get<CardViewModel> { parametersOf(state) }
    }
}
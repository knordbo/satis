package com.satis.app.feature.cards

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.BuildConfig
import com.satis.app.feature.cards.data.Card
import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.utils.coroutines.BaseViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CardViewModel @AssistedInject constructor(
        @Assisted initialSate: CardState,
        private val cardRepository: CardRepository
) : BaseViewModel<CardState>(
        initialState = initialSate,
        debugMode = BuildConfig.DEBUG
) {
    init {
        logStateChanges()
        getCards()
    }

    fun addCard(card: Card) {
        cardRepository.addCard(card)
    }

    fun like(id: String, like: Boolean) {
        cardRepository.like(id, like)
    }

    fun dislike(id: String, dislike: Boolean) {
        cardRepository.dislike(id, dislike)
    }

    fun removeCard(id: String) {
        cardRepository.removeCard(id)
    }

    private fun getCards() {
        launch {
            cardRepository.getCards().collect { cards ->
                setState {
                    copy(cards = cards.sortedByDescending { it.likes })
                }
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialSate: CardState): CardViewModel
    }

    companion object : MvRxViewModelFactory<CardViewModel, CardState> {
        override fun create(viewModelContext: ViewModelContext, state: CardState): CardViewModel? {
            val fragment: CardFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.createViewModel(state)
        }
    }
}
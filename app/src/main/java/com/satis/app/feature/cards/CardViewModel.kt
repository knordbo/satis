package com.satis.app.feature.cards

import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.satis.app.BuildConfig
import com.satis.app.feature.cards.data.Card
import com.satis.app.feature.cards.data.CardProvider
import io.reactivex.Scheduler
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class CardViewModel(
        initialSate: CardState,
        private val cardProvider: CardProvider,
        private val ioScheduler: Scheduler
) : BaseMvRxViewModel<CardState>(
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
        cardProvider.getCards()
                .subscribeOn(ioScheduler)
                .map { cards -> cards.sortedByDescending { it.likes } }
                .toObservable().execute {
                    copy(cards = it() ?: cards)
                }
    }

    companion object : MvRxViewModelFactory<CardState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: CardState): BaseMvRxViewModel<CardState> =
                activity.get<CardViewModel> { parametersOf(state) }
    }
}
package com.satis.app.feature.cards.redux

import com.satis.app.feature.cards.data.Card
import com.satis.app.redux.ReduxDipatcher
import com.satis.app.redux.Store
import io.reactivex.Scheduler

class CardDispatcher(
        private val cardMiddleware: CardMiddleware,
        colorStore: Store<CardState, CardActions>,
        scheduler: Scheduler)
    : ReduxDipatcher<CardState, CardActions, CardViewState>(
        colorStore,
        scheduler,
        ::cardStateToCardViewState) {

    init {
        store.dispatch(cardMiddleware.getCards())
    }

    fun addCard(card: Card) {
        store.dispatch(cardMiddleware.addCard(card))
    }

    fun like(id: String, like: Boolean) {
        store.dispatch(cardMiddleware.like(id, like))
    }

    fun dislike(id: String, dislike: Boolean) {
        store.dispatch(cardMiddleware.dislike(id, dislike))
    }

}
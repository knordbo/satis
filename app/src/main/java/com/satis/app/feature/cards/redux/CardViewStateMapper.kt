package com.satis.app.feature.cards.redux

fun cardStateToCardViewState(cardState: CardState) = with(cardState) {
    CardViewState(
            cards = cards
    )
}
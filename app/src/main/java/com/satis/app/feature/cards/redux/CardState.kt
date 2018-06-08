package com.satis.app.feature.cards.redux

import com.satis.app.feature.cards.data.Card

data class CardState(
        val cards: List<Card> = emptyList()
)

val INITIAL_COLOR_STATE = CardState()
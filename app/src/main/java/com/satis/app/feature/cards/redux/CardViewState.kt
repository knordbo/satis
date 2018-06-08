package com.satis.app.feature.cards.redux

import com.satis.app.feature.cards.data.Card

data class CardViewState(
        val cards: List<Card> = emptyList()
)
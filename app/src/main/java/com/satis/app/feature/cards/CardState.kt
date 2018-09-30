package com.satis.app.feature.cards

import com.airbnb.mvrx.MvRxState
import com.satis.app.feature.cards.data.Card

data class CardState(val cards: List<Card> = emptyList()) : MvRxState
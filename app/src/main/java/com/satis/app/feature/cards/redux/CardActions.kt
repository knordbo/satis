package com.satis.app.feature.cards.redux

import com.satis.app.feature.cards.data.Card

sealed class CardActions {
    data class CardResult(val cards: List<Card>) : CardActions()
    object CardLiked : CardActions()
    object CardDisliked : CardActions()
    object CardAdded : CardActions()
}
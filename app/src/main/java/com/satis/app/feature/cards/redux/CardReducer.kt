package com.satis.app.feature.cards.redux

import com.satis.app.redux.Reducer

object CardReducer : Reducer<CardState, CardActions> {
    override fun reduce(oldState: CardState, action: CardActions): CardState = when (action) {
        is CardActions.CardResult -> oldState.copy(cards = action.cards)
        CardActions.CardLiked -> oldState
        CardActions.CardDisliked -> oldState
        CardActions.CardAdded -> oldState
        CardActions.CardRemoved -> oldState
    }
}
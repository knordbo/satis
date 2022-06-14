package com.satis.app.feature.cards

import com.satis.app.feature.cards.data.Card

data class CardState(
  val cards: List<Card> = emptyList(),
  val creatingCardDialogOpen: Boolean = false,
  val creatingCard: Card = Card(title = "", message = ""),
  val creatingCardEvent: CreatingCardEvent = CreatingCardEvent.None,
)

sealed class CreatingCardEvent {
  object None : CreatingCardEvent()
  object NoTitle : CreatingCardEvent()
  object Success : CreatingCardEvent()
}
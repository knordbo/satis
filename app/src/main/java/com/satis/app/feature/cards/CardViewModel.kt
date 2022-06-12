package com.satis.app.feature.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satis.app.feature.cards.data.Card
import com.satis.app.feature.cards.data.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class CardViewModel @Inject constructor(
  private val cardRepository: CardRepository,
) : ViewModel(), CoroutineScope {

  override val coroutineContext: CoroutineContext
    get() = viewModelScope.coroutineContext

  private val _state: MutableStateFlow<CardState> = MutableStateFlow(value = CardState())
  val state: StateFlow<CardState> = _state.asStateFlow()

  fun load() {
    getCards()
  }

  fun addCardTitleChanged(title: String) {
    setState {
      copy(creatingCard = creatingCard.copy(title = title))
    }
  }

  fun addCardMessageChanged(message: String) {
    setState {
      copy(creatingCard = creatingCard.copy(message = message))
    }
  }

  fun addCard() {
    setState {
      if (creatingCard.title.isBlank()) {
        copy(creatingCardEvent = CreatingCardEvent.NoTitle)
      } else {
        cardRepository.addCard(creatingCard)
        copy(
          creatingCard = Card(title = "", message = ""),
          creatingCardEvent = CreatingCardEvent.Success
        )
      }
    }
  }

  fun addCardEventHandled() {
    setState {
      copy(creatingCardEvent = CreatingCardEvent.None)
    }
  }

  fun like(card: Card) {
    cardRepository.like(card.id, !card.hasLiked)
  }

  fun dislike(card: Card) {
    cardRepository.dislike(card.id, !card.hasDisliked)
  }

  fun removeCard(card: Card) {
    cardRepository.removeCard(card.id)
  }

  private fun getCards() {
    launch {
      cardRepository.getCards().collect { cards ->
        setState {
          copy(cards = cards.sortedByDescending { card -> card.likes })
        }
      }
    }
  }

  private fun setState(update: CardState.() -> CardState) {
    _state.value = _state.value.update()
  }
}
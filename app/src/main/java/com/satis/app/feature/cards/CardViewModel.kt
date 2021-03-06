package com.satis.app.feature.cards

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.feature.cards.data.Card
import com.satis.app.feature.cards.data.CardRepository
import com.satis.app.utils.coroutines.BaseViewModel
import com.satis.app.utils.coroutines.viewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CardViewModel @AssistedInject constructor(
  @Assisted initialState: CardState,
  private val cardRepository: CardRepository
) : BaseViewModel<CardState>(
  initialState = initialState
) {
  init {
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
    withState { state ->
      if (state.creatingCard.title.isBlank()) {
        setState {
          copy(creatingCardAsync = Fail(IllegalArgumentException()))
        }
      } else {
        cardRepository.addCard(state.creatingCard)
        setState {
          copy(
            creatingCard = Card(title = "", message = ""),
            creatingCardAsync = Success(state.creatingCard)
          )
        }
        setState {
          copy(creatingCardAsync = Uninitialized)
        }
      }
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

  interface Factory {
    fun createCardViewModel(initialState: CardState): CardViewModel
  }

  @AssistedFactory
  interface FactoryImpl : Factory

  companion object : MvRxViewModelFactory<CardViewModel, CardState> {
    override fun create(viewModelContext: ViewModelContext, state: CardState): CardViewModel {
      return viewModelContext.viewModelFactory<Factory>().createCardViewModel(state)
    }
  }
}
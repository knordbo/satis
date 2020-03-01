package com.satis.app.feature.cards.data

import kotlinx.coroutines.flow.Flow

interface CardRepository {
  fun getCards(): Flow<List<Card>>
  fun addCard(card: Card)
  fun removeCard(id: String)
  fun like(id: String, like: Boolean)
  fun dislike(id: String, dislike: Boolean)
}
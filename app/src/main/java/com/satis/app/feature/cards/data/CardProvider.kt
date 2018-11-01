package com.satis.app.feature.cards.data

import kotlinx.coroutines.channels.ReceiveChannel

interface CardProvider {
    fun getCards(): ReceiveChannel<List<Card>>
    fun addCard(card: Card)
    fun removeCard(id: String)
    fun like(id: String, like: Boolean)
    fun dislike(id: String, dislike: Boolean)
}
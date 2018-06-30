package com.satis.app.feature.cards.data

import io.reactivex.Flowable

interface CardProvider {
    fun getCards(): Flowable<List<Card>>
    fun addCard(card: Card)
    fun removeCard(id: String)
    fun like(id: String, like: Boolean)
    fun dislike(id: String, dislike: Boolean)
}
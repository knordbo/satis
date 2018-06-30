package com.satis.app.feature.cards.redux

import com.satis.app.feature.cards.data.Card
import com.satis.app.feature.cards.data.CardProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class CardMiddleware(private val cardProvider: CardProvider) {
    fun getCards(): Flowable<CardActions> = cardProvider.getCards()
            .map {
                CardActions.CardResult(it.sortedByDescending { it.likes })
            }
            .cast(CardActions::class.java)
            .subscribeOn(Schedulers.io())

    fun addCard(card: Card): Flowable<CardActions> = Completable.fromCallable { cardProvider.addCard(card) }
            .andThen(Flowable.just(CardActions.CardAdded))
            .cast(CardActions::class.java)
            .subscribeOn(Schedulers.io())

    fun removeCard(id: String): Flowable<CardActions> = Completable.fromCallable { cardProvider.removeCard(id) }
            .andThen(Flowable.just(CardActions.CardRemoved))
            .cast(CardActions::class.java)
            .subscribeOn(Schedulers.io())

    fun like(id: String, like: Boolean): Flowable<CardActions> = Completable.fromCallable { cardProvider.like(id, like) }
            .andThen(Flowable.just(CardActions.CardLiked))
            .cast(CardActions::class.java)
            .subscribeOn(Schedulers.io())

    fun dislike(id: String, dislike: Boolean): Flowable<CardActions> = Completable.fromCallable { cardProvider.dislike(id, dislike) }
            .andThen(Flowable.just(CardActions.CardDisliked))
            .cast(CardActions::class.java)
            .subscribeOn(Schedulers.io())
}
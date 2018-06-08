package com.satis.app.feature.cards.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class DefaultCardProvider(private val userId: String, firebaseFirestore: FirebaseFirestore) : CardProvider {

    private val cardsCollection = firebaseFirestore.collection(CARDS_COLLECTION)

    override fun getCards(): Flowable<List<Card>> = Flowable.create({ emitter ->
        val listener = cardsCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (!emitter.isCancelled && querySnapshot != null) {
                val cards = querySnapshot.documents
                        .mapNotNull {
                            it.toObject(DbCard::class.java)?.toModel(it.id, userId)
                        }
                emitter.onNext(cards)
            }
        }
        if (!emitter.isCancelled) {
            emitter.setCancellable {
                listener.remove()
            }
        }
    }, BackpressureStrategy.LATEST)

    override fun addCard(card: Card) {
        cardsCollection.add(card.toDb())
    }

    override fun like(id: String, like: Boolean) {
        setDiff(id, mapOf(LIKES to mapOf(userId to like)))
    }

    override fun dislike(id: String, dislike: Boolean) {
        setDiff(id, mapOf(DISLIKES to mapOf(userId to dislike)))
    }

    private fun setDiff(id: String, diff: Any) {
        cardsCollection.document(id).set(diff, SetOptions.merge())
    }

}

private const val CARDS_COLLECTION = "cards"
private const val LIKES = "likes"
private const val DISLIKES = "dislikes"
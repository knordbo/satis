package com.satis.app.feature.cards.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class DefaultCardProvider(private val userId: String, firebaseFirestore: FirebaseFirestore) : CardProvider {

    private val cardsCollection by lazy {
        firebaseFirestore.collection(CARDS_COLLECTION)
    }

    override fun getCards(): Flow<List<Card>> = channelFlow {
        val listener = cardsCollection.addSnapshotListener { querySnapshot, _ ->
            if (querySnapshot != null && !isClosedForSend) {
                val cards = querySnapshot.documents
                        .mapNotNull {
                            it.toObject(DbCard::class.java)?.toModel(it.id, userId)
                        }
                offer(cards)
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    override fun addCard(card: Card) {
        cardsCollection.add(card.toDb())
    }

    override fun removeCard(id: String) {
        cardsCollection.document(id).delete()
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
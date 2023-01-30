package com.satis.app.feature.cards.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.satis.app.common.annotations.Io
import com.satis.app.common.account.AccountId
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CardRepositoryImpl @AssistedInject constructor(
  @Assisted val accountId: AccountId,
  firebaseFirestore: FirebaseFirestore,
  @Io private val io: CoroutineContext
) : CardRepository {

  @AssistedFactory
  interface Factory {
    fun create(accountId: AccountId): CardRepositoryImpl
  }

  private val cardsCollection by lazy { firebaseFirestore.collection(CARDS_COLLECTION) }

  override fun getCards(): Flow<List<Card>> = channelFlow {
    val listener = cardsCollection.addSnapshotListener { querySnapshot, _ ->
      if (querySnapshot != null && !isClosedForSend) {
        launch(io) {
          val cards = querySnapshot.documents
            .mapNotNull {
              it.toObject(DbCard::class.java)?.toModel(it.id, accountId)
            }
          trySend(cards).isSuccess
        }
      }
    }
    awaitClose {
      listener.remove()
    }
  }.flowOn(io)

  override fun addCard(card: Card) {
    cardsCollection.add(card.toDb())
  }

  override fun removeCard(id: String) {
    cardsCollection.document(id).delete()
  }

  override fun like(id: String, like: Boolean) {
    setDiff(id, mapOf(LIKES to mapOf(accountId.value to like)))
  }

  override fun dislike(id: String, dislike: Boolean) {
    setDiff(id, mapOf(DISLIKES to mapOf(accountId.value to dislike)))
  }

  private fun setDiff(id: String, diff: Any) {
    cardsCollection.document(id).set(diff, SetOptions.merge())
  }

}

private const val CARDS_COLLECTION = "cards"
private const val LIKES = "likes"
private const val DISLIKES = "dislikes"
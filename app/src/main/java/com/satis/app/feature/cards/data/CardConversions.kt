package com.satis.app.feature.cards.data

import com.satis.app.common.prefs.AccountId

fun DbCard.toModel(id: String, accountId: AccountId) = Card(
  id = id,
  message = message,
  title = title,
  likes = likes.count { it.value },
  dislikes = dislikes.count { it.value },
  hasLiked = likes.accountIdInListAndTrue(accountId),
  hasDisliked = dislikes.accountIdInListAndTrue(accountId)
)

fun Card.toDb() = DbCard(title = title, message = message)

private fun Map<String, Boolean>.accountIdInListAndTrue(accountId: AccountId) =
  filter { it.key == accountId.value && it.value }.isNotEmpty()
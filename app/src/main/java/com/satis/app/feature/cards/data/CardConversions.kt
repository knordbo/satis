package com.satis.app.feature.cards.data

import com.satis.app.common.prefs.UserId

fun DbCard.toModel(id: String, userId: UserId) = Card(
  id = id,
  message = message,
  title = title,
  likes = likes.count { it.value },
  dislikes = dislikes.count { it.value },
  hasLiked = likes.userIdInListAndTrue(userId),
  hasDisliked = dislikes.userIdInListAndTrue(userId)
)

fun Card.toDb() = DbCard(title = title, message = message)

private fun Map<String, Boolean>.userIdInListAndTrue(userId: UserId) = filter { it.key == userId.value && it.value }.isNotEmpty()
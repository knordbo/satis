package com.satis.app.feature.cards.data

fun DbCard.toModel(id: String, userId: String) = Card(
        id = id,
        message = message,
        title = title,
        likes = likes.count { it.value },
        dislikes = dislikes.count { it.value },
        hasLiked = likes.userIdInListAndTrue(userId),
        hasDisliked = dislikes.userIdInListAndTrue(userId)
)

fun Card.toDb() = DbCard(title = title, message = message)

private fun Map<String, Boolean>.userIdInListAndTrue(userId: String) = filter { it.key == userId && it.value }.isNotEmpty()
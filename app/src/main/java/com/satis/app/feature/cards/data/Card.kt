package com.satis.app.feature.cards.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    val id: String = "",
    val title: String,
    val message: String,
    val likes: Int = 0,
    val dislikes: Int = 0,
    val hasLiked: Boolean = false,
    val hasDisliked: Boolean = false
) : Parcelable
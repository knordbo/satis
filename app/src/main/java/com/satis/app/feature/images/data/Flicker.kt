package com.satis.app.feature.images.data

import kotlinx.serialization.Serializable

@Serializable
data class Flicker(val photos: Photos)

@Serializable
data class Photos(val photo: List<Photo>)

@Serializable
data class Photo(
        val id: String,
        val secret: String,
        val server: String,
        val farm: Long
)
package com.satis.app.feature.images.data

import kotlinx.serialization.Serializable

@Serializable
data class Unsplash(val photos: List<UnsplashPhoto>)

@Serializable
data class UnsplashPhoto(
        val id: String,
        val urls: Urls
)

@Serializable
data class Urls(
        val regular: String,
        val thumb: String
)
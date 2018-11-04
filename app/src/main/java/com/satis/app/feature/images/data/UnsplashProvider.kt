package com.satis.app.feature.images.data

import com.satis.app.feature.images.PhotoState
import kotlinx.coroutines.channels.ReceiveChannel

interface UnsplashProvider {
    suspend fun fetchPhotos(query: String): List<PhotoState>
    fun streamPhotos(query: String): ReceiveChannel<List<PhotoState>>
}

const val NATURE = "nature"
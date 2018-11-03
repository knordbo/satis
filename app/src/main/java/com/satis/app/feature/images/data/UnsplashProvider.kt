package com.satis.app.feature.images.data

import com.satis.app.feature.images.PhotoState
import kotlinx.coroutines.channels.ReceiveChannel

interface UnsplashProvider {
    suspend fun fetchCuratedPhotos(): List<PhotoState>
    fun streamCuratedPhotos(): ReceiveChannel<List<PhotoState>>
}
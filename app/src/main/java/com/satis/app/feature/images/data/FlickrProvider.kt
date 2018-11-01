package com.satis.app.feature.images.data

import com.satis.app.feature.images.PhotoState
import kotlinx.coroutines.channels.ReceiveChannel

interface FlickrProvider {
    suspend fun fetchPopularImages(): List<PhotoState>
    fun streamPopularImages(): ReceiveChannel<List<PhotoState>>
}
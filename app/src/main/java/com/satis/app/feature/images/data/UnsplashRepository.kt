package com.satis.app.feature.images.data

import com.satis.app.feature.images.PhotoState
import kotlinx.coroutines.flow.Flow

interface UnsplashRepository {
  suspend fun fetchPhotos(query: String): List<PhotoState>
  fun streamPhotos(query: String): Flow<List<PhotoState>>
}

const val NATURE = "nature"
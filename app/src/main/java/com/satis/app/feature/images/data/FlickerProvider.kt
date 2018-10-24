package com.satis.app.feature.images.data

import com.satis.app.feature.images.PhotoState

interface FlickerProvider {
    suspend fun getRecentImages(): List<PhotoState>
}
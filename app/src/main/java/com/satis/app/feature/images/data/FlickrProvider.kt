package com.satis.app.feature.images.data

import com.satis.app.feature.images.PhotoState
import io.reactivex.Flowable

interface FlickrProvider {
    suspend fun fetchPopularImages(): List<PhotoState>
    fun streamPopularImages(): Flowable<List<PhotoState>>
}
package com.satis.app.feature.images.data

import android.net.Uri
import com.satis.app.feature.images.PhotoState

class DefaultFlickerProvider(private val flickerApi: FlickerApi) : FlickerProvider {
    override suspend fun getRecentImages(): List<PhotoState> = flickerApi.getRecentImages().await().photos.photo.map {
        PhotoState(it.id, it.toUri())
    }

    private fun Photo.toUri() = Uri.parse("https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg")
}
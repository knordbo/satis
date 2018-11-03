package com.satis.app.feature.images.data

import android.net.Uri
import com.satis.app.common.keyvalue.Key
import com.satis.app.common.keyvalue.KeyValueProvider
import com.satis.app.feature.images.PhotoState
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map

class DefaultUnsplashProvider(
        private val unsplashApi: UnsplashApi,
        private val keyValueProvider: KeyValueProvider
) : UnsplashProvider {
    private val curatedPhotosKey: Key<Unsplash> = Key.of("unsplash_curated_photos")

    override suspend fun fetchCuratedPhotos(): List<PhotoState> {
        val curatedPhotos = Unsplash(unsplashApi.getCuratedPhotos().await())
        keyValueProvider.insert(curatedPhotosKey, curatedPhotos)
        return curatedPhotos.toState()
    }

    override fun streamCuratedPhotos(): ReceiveChannel<List<PhotoState>> = keyValueProvider.getStream(curatedPhotosKey).map { unsplash ->
        unsplash.toState()
    }

    private fun Unsplash.toState() = photos.map {
        PhotoState(
                id = it.id,
                thumbnailUrl = Uri.parse(it.urls.thumb),
                photoUrl = Uri.parse(it.urls.regular)
        )
    }
}
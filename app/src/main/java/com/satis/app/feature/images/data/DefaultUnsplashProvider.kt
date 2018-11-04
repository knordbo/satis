package com.satis.app.feature.images.data

import android.net.Uri
import com.satis.app.common.keyvalue.Key
import com.satis.app.common.keyvalue.KeyValueProvider
import com.satis.app.feature.images.PhotoState
import com.satis.app.feature.images.User
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map

class DefaultUnsplashProvider(
        private val unsplashApi: UnsplashApi,
        private val keyValueProvider: KeyValueProvider
) : UnsplashProvider {

    override suspend fun fetchPhotos(query: String): List<PhotoState> {
        val photos = unsplashApi.searchPhotos(query = query).await()
        keyValueProvider.insert(photosKey(query), photos)
        return photos.toState()
    }

    override fun streamPhotos(query: String): ReceiveChannel<List<PhotoState>> = keyValueProvider.getStream(photosKey(query)).map { unsplash ->
        unsplash.toState()
    }

    private fun photosKey(query: String): Key<Unsplash> = Key.of("unsplash_photos_$query")

    private fun Unsplash.toState() = results.map {
        PhotoState(
                id = it.id,
                thumbnailUrl = Uri.parse(it.urls.thumb),
                photoUrl = Uri.parse(it.urls.regular),
                user = User(
                        username = it.user.username,
                        userAvatar = Uri.parse(it.user.profileImage.medium)
                )
        )
    }
}
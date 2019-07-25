package com.satis.app.feature.images.data

import android.net.Uri
import com.satis.app.common.keyvalue.Key
import com.satis.app.common.keyvalue.KeyValueProvider
import com.satis.app.feature.images.PhotoState
import com.satis.app.feature.images.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultUnsplashProvider @Inject constructor(
        private val unsplashApi: UnsplashApi,
        private val keyValueProvider: KeyValueProvider
) : UnsplashProvider {

    override suspend fun fetchPhotos(query: String): List<PhotoState> {
        val photos = unsplashApi.searchPhotos(query = query)
        keyValueProvider.insert(photosKey(query), photos)
        return photos.toState()
    }

    override fun streamPhotos(query: String): Flow<List<PhotoState>> = keyValueProvider.getStream(photosKey(query)).map { unsplash ->
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
                ),
                description = it.description
        )
    }
}
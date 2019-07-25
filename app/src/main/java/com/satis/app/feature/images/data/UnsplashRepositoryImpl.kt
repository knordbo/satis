package com.satis.app.feature.images.data

import android.net.Uri
import com.satis.app.common.keyvalue.Key
import com.satis.app.common.keyvalue.KeyValueRepository
import com.satis.app.feature.images.PhotoState
import com.satis.app.feature.images.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
        private val unsplashApi: UnsplashApi,
        private val keyValueRepository: KeyValueRepository
) : UnsplashRepository {

    override suspend fun fetchPhotos(query: String): List<PhotoState> {
        val photos = unsplashApi.searchPhotos(query = query)
        keyValueRepository.insert(photosKey(query), photos)
        return photos.toState()
    }

    override fun streamPhotos(query: String): Flow<List<PhotoState>> = keyValueRepository.getStream(photosKey(query)).map { unsplash ->
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
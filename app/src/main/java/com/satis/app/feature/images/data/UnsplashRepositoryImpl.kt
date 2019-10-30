package com.satis.app.feature.images.data

import android.net.Uri
import com.satis.app.common.annotations.Io
import com.satis.app.feature.images.PhotoState
import com.satis.app.feature.images.User
import com.satis.app.feature.images.data.db.UnsplashPhotoEntity
import com.satis.app.feature.images.data.db.UnsplashQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class UnsplashRepositoryImpl @Inject constructor(
        @Io private val io: CoroutineContext,
        private val unsplashApi: UnsplashApi,
        private val unsplashQueries: UnsplashQueries
) : UnsplashRepository {

    override suspend fun fetchPhotos(query: String): List<PhotoState> {
        return withContext(io) {
            val photos = unsplashApi.searchPhotos(query = query)
            unsplashQueries.transaction {
                unsplashQueries.deleteAll()
                for (photo in photos.results) {
                    unsplashQueries.insertUnsplashPhoto(
                            id = photo.id,
                            urlRegular = photo.urls.regular,
                            urlThumb = photo.urls.thumb,
                            userUsername = photo.user.username,
                            userProfileImageMedium = photo.user.profileImage.medium,
                            description = photo.description
                    )
                }
            }
            photos.toState()
        }
    }

    override fun streamPhotos(query: String): Flow<List<PhotoState>> = unsplashQueries.selectAll().asFlow().map { unsplash ->
        unsplash.executeAsList().toState()
    }.flowOn(io)

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

    private fun List<UnsplashPhotoEntity>.toState() = map {
        PhotoState(
                id = it.id,
                thumbnailUrl = Uri.parse(it.urlThumb),
                photoUrl = Uri.parse(it.urlRegular),
                user = User(
                        username = it.userUsername,
                        userAvatar = Uri.parse(it.userProfileImageMedium)
                ),
                description = it.description
        )
    }
}
package com.satis.app.feature.images.data

import android.net.Uri
import app.cash.sqldelight.coroutines.asFlow
import com.satis.app.common.annotations.Io
import com.satis.app.feature.images.PhotoState
import com.satis.app.feature.images.User
import com.satis.app.feature.images.data.db.UnsplashPhotoEntity
import com.satis.app.feature.images.data.db.UnsplashQueries
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
  private val unsplashQueries: UnsplashQueries,
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

  override fun streamPhotos(query: String): Flow<List<PhotoState>> =
    unsplashQueries.selectAll().asFlow().map { unsplash ->
      unsplash.executeAsList().toState()
    }.flowOn(io)

  private fun Unsplash.toState() = results.map { unsplashPhoto ->
    PhotoState(
      id = unsplashPhoto.id,
      thumbnailUrl = Uri.parse(unsplashPhoto.urls.thumb),
      photoUrl = Uri.parse(unsplashPhoto.urls.regular),
      user = User(
        username = unsplashPhoto.user.username,
        userAvatar = Uri.parse(unsplashPhoto.user.profileImage.medium)
      ),
      description = unsplashPhoto.description
    )
  }

  private fun List<UnsplashPhotoEntity>.toState() = map { unsplashPhoto ->
    PhotoState(
      id = unsplashPhoto.id,
      thumbnailUrl = Uri.parse(unsplashPhoto.urlThumb),
      photoUrl = Uri.parse(unsplashPhoto.urlRegular),
      user = User(
        username = unsplashPhoto.userUsername,
        userAvatar = Uri.parse(unsplashPhoto.userProfileImageMedium)
      ),
      description = unsplashPhoto.description
    )
  }
}
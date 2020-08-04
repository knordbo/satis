package com.satis.app.feature.images.startup

import android.content.Context
import coil.Coil
import coil.request.CachePolicy
import coil.request.GetRequest
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.startup.StartupTask
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UnsplashPreloadTask @Inject constructor(
  private val context: Context,
  private val unsplashRepository: UnsplashRepository
) : StartupTask {
  override suspend fun execute() {
    unsplashRepository.streamPhotos(NATURE)
      .firstOrNull()
      ?.take(PRELOAD_IMAGE_COUNT)
      ?.forEach { photo ->
        // Load up the images from disk if there, increasing the chance it will be in
        // memory by the time it is accessed.
        Coil.imageLoader(context)
          .execute(GetRequest.Builder(context)
            .data(photo.photoUrl)
            .networkCachePolicy(CachePolicy.DISABLED)
            .build())
      }
  }
}

private const val PRELOAD_IMAGE_COUNT = 20

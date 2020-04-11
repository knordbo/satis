package com.satis.app.feature.images.startup

import coil.Coil
import coil.api.get
import coil.request.CachePolicy
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.startup.StartupTask
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UnsplashTask @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) : StartupTask {
  override suspend fun execute() {
    unsplashRepository.streamPhotos(NATURE)
        .firstOrNull()
        ?.take(PRELOAD_IMAGE_COUNT)
        ?.forEach { photo ->
          Coil.get(photo.photoUrl) {
            networkCachePolicy(CachePolicy.DISABLED)
          }
        }
  }
}

private const val PRELOAD_IMAGE_COUNT = 20

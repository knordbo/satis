package com.satis.app.feature.images.startup

import android.content.Context
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.satis.app.common.account.AccountId
import com.satis.app.common.annotations.Io
import com.satis.app.common.annotations.MostRecentCurrentAccount
import com.satis.app.di.account.AccountProvider
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.startup.StartupTask
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UnsplashPreloadTask @Inject constructor(
  @ApplicationContext private val context: Context,
  @MostRecentCurrentAccount private val mostRecentCurrentAccountId: AccountId,
  @Io private val io: CoroutineContext,
  private val unsplashRepositoryProvider: AccountProvider<UnsplashRepository>,
  private val imageLoader: ImageLoader,
) : StartupTask {
  override suspend fun execute() {
    withContext(io) {
      unsplashRepositoryProvider.get(mostRecentCurrentAccountId).streamPhotos(NATURE)
        .firstOrNull()
        ?.take(PRELOAD_IMAGE_COUNT)
        ?.forEach { photo ->
          // Load up the images from disk if there, increasing the chance it will be in
          // memory by the time it is accessed.
          imageLoader
            .execute(ImageRequest.Builder(context)
              .data(photo.photoUrl)
              .networkCachePolicy(CachePolicy.DISABLED)
              .build())
        }
    }
  }
}

private const val PRELOAD_IMAGE_COUNT = 20

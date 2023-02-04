package com.satis.app.feature.images.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.ImageLoader
import coil.request.ImageRequest
import com.satis.app.common.account.AccountId
import com.satis.app.common.annotations.MostRecentCurrentAccount
import com.satis.app.common.annotations.WorkerIo
import com.satis.app.common.logging.Logger
import com.satis.app.di.account.UnsplashRepositoryProvider
import com.satis.app.feature.images.data.NATURE
import com.satis.app.utils.lifecycle.isAppForegroundString
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.CoroutineContext
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltWorker
class ImageWorker @AssistedInject constructor(
  @Assisted private val context: Context,
  @Assisted private val workerParameters: WorkerParameters,
  @WorkerIo private val io: CoroutineContext,
  @MostRecentCurrentAccount val mostRecentCurrentAccountId: AccountId,
  private val logger: Logger,
  private val unsplashRepositoryProvider: UnsplashRepositoryProvider,
  private val imageLoader: ImageLoader,
) : CoroutineWorker(context, workerParameters) {

  override suspend fun doWork(): Result = withContext(io) {
    try {
      val type = if (PERIODIC in workerParameters.tags) "periodic" else "one time"
      logger.log(LOG_TAG,
        "Starting $type worker in $isAppForegroundString on thread: ${Thread.currentThread()}")

      val popularImages =
        unsplashRepositoryProvider.get(mostRecentCurrentAccountId).fetchPhotos(NATURE)
      popularImages
        .take(FETCH_IMAGE_COUNT)
        .forEach { photo ->
          withTimeoutOrNull(5.toDuration(DurationUnit.SECONDS)) {
            imageLoader
              .execute(ImageRequest.Builder(context)
                .data(photo.photoUrl)
                .build())
          }
        }

      logger.log(LOG_TAG, "Success")
      Result.success()
    } catch (t: Throwable) {
      logger.log(LOG_TAG, "Failure")
      Result.failure()
    }
  }
}

const val PERIODIC = "periodic"

private const val FETCH_IMAGE_COUNT = 20
private const val LOG_TAG = "ImageWorker"
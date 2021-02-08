package com.satis.app.feature.images.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.imageLoader
import coil.request.ImageRequest
import com.satis.app.common.annotations.Io
import com.satis.app.common.logging.Logger
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.utils.lifecycle.isAppForegroundString
import com.satis.app.work.ChildWorkerFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.CoroutineContext
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

class ImageWorker @AssistedInject constructor(
  @Assisted private val context: Context,
  @Assisted workerParameters: WorkerParameters,
  @Io private val io: CoroutineContext,
  private val logger: Logger,
  private val unsplashRepository: UnsplashRepository,
) : CoroutineWorker(context, workerParameters) {

  @ExperimentalTime
  override suspend fun doWork(): Result = withContext(io) {
    try {
      logger.log(LOG_TAG, "Starting in $isAppForegroundString")

      val popularImages = unsplashRepository.fetchPhotos(NATURE)
      popularImages
        .take(FETCH_IMAGE_COUNT)
        .forEach { photo ->
          withTimeoutOrNull(5.toDuration(DurationUnit.SECONDS)) {
            context.imageLoader
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

  @AssistedFactory
  interface Factory : ChildWorkerFactory {
    override fun create(context: Context, workerParameters: WorkerParameters): ImageWorker
  }

}

private const val FETCH_IMAGE_COUNT = 20
private const val LOG_TAG = "ImageWorker"
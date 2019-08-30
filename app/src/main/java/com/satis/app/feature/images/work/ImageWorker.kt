package com.satis.app.feature.images.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.satis.app.common.annotations.Io
import com.satis.app.common.logging.Logger
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashRepository
import com.satis.app.utils.lifecycle.isAppForegroundString
import com.satis.app.work.ChildWorkerFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class ImageWorker @AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted workerParameters: WorkerParameters,
        @Io private val io: CoroutineContext,
        private val logger: Logger,
        private val unsplashRepository: UnsplashRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(io) {
        try {
            logger.log(LOG_TAG, "Starting in $isAppForegroundString")

            val popularImages = unsplashRepository.fetchPhotos(NATURE)
            val requestManager = Glide.with(context)
            popularImages
                    .take(FETCH_IMAGE_COUNT)
                    .forEach { photo ->
                        requestManager
                                .load(photo.photoUrl)
                                .thumbnail(requestManager.load(photo.thumbnailUrl)
                                        .centerCrop())
                                .centerCrop()
                                .submit()
                                .get(5, TimeUnit.SECONDS)
                    }

            logger.log(LOG_TAG, "Success")
            Result.success()
        } catch (t: Throwable) {
            logger.log(LOG_TAG, "Failure")
            Result.failure()
        }
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory

}

private const val FETCH_IMAGE_COUNT = 10
private const val LOG_TAG = "ImageWorker"
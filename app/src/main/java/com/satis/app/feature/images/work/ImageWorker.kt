package com.satis.app.feature.images.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.satis.app.Io
import com.satis.app.common.logging.Logger
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashProvider
import com.satis.app.utils.lifecycle.isAppForegroundString
import com.satis.app.work.ChildWorkerFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class ImageWorker @AssistedInject constructor(
        @Assisted private val context: Context,
        @Assisted workerParameters: WorkerParameters,
        @Io private val io: CoroutineDispatcher,
        private val logger: Logger,
        private val unsplashProvider: UnsplashProvider
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(io) {
        try {
            logger.log(LOG_TAG, "Starting in $isAppForegroundString")

            val popularImages = unsplashProvider.fetchPhotos(NATURE)
            val requestManager = Glide.with(context)
            popularImages.forEachIndexed { index, photo ->
                if (index < FETCH_IMAGE_COUNT) {
                    requestManager
                            .load(photo.photoUrl)
                            .thumbnail(requestManager.load(photo.thumbnailUrl)
                                    .centerCrop())
                            .centerCrop()
                            .submit()
                            .get(5, TimeUnit.SECONDS)
                }
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
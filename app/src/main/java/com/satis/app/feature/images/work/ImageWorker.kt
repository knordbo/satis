package com.satis.app.feature.images.work

import android.content.Context
import androidx.work.ListenableWorker.Result.FAILURE
import androidx.work.ListenableWorker.Result.SUCCESS
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.satis.app.common.logging.Logger
import com.satis.app.feature.images.data.NATURE
import com.satis.app.feature.images.data.UnsplashProvider
import com.satis.app.work.CoroutineWorker
import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.TimeUnit

class ImageWorker(
        private val context: Context,
        workerParameters: WorkerParameters,
        coroutineDispatcher: CoroutineDispatcher,
        private val logger: Logger,
        private val unsplashProvider: UnsplashProvider
) : CoroutineWorker(context, workerParameters, coroutineDispatcher) {
    override suspend fun work(): Payload {
        return Payload(try {
            logger.log(LOG_TAG, "Starting")

            val popularImages = unsplashProvider.fetchPhotos(NATURE)
            val requestManager = Glide.with(context)
            popularImages.forEachIndexed { index, photo ->
                if (index < FETCH_IMAGE_COUNT) {
                    requestManager
                            .load(photo.photoUrl)
                            .thumbnail(requestManager.load(photo.thumbnailUrl)
                                    .apply(RequestOptions.centerCropTransform()))
                            .apply(RequestOptions.centerCropTransform())
                            .submit()
                            .get(5, TimeUnit.SECONDS)
                }
            }

            logger.log(LOG_TAG, "Success")
            SUCCESS
        } catch (t: Throwable) {
            logger.log(LOG_TAG, "Failure")
            FAILURE
        })
    }
}

private const val FETCH_IMAGE_COUNT = 10
private const val LOG_TAG = "ImageWorker"
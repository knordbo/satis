package com.satis.app.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.satis.app.common.logging.Logger
import com.satis.app.utils.lifecycle.isAppForegroundString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NetworkWorker(
        context: Context,
        workerParameters: WorkerParameters,
        private val io: CoroutineDispatcher,
        private val logger: Logger
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(io) {
        try {
            logger.log(LOG_TAG, "Starting in $isAppForegroundString")
            logger.log(LOG_TAG, "Success")
            Result.success()
        } catch (t: Throwable) {
            logger.log(LOG_TAG, "Failure")
            Result.failure()
        }
    }

}

private const val LOG_TAG = "NetWorker"
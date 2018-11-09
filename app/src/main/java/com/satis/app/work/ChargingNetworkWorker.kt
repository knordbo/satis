package com.satis.app.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.FAILURE
import androidx.work.ListenableWorker.Result.SUCCESS
import androidx.work.WorkerParameters
import com.satis.app.common.logging.Logger
import kotlinx.coroutines.CoroutineDispatcher

class ChargingNetworkWorker(
        context: Context,
        workerParameters: WorkerParameters,
        override val coroutineContext: CoroutineDispatcher,
        private val logger: Logger
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Payload = Payload(try {
        logger.log(LOG_TAG, "Starting")
        Thread.sleep(5000)
        logger.log(LOG_TAG, "Success")
        SUCCESS
    } catch (t: Throwable) {
        logger.log(LOG_TAG, "Failure")
        FAILURE
    })

}

private const val LOG_TAG = "CharNetWorker"
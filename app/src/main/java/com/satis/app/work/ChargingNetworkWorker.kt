package com.satis.app.work

import android.content.Context
import androidx.work.ListenableWorker.Result.FAILURE
import androidx.work.ListenableWorker.Result.SUCCESS
import androidx.work.WorkerParameters
import com.satis.app.common.Prefs
import kotlinx.coroutines.CoroutineDispatcher

class ChargingNetworkWorker(
        context: Context,
        workerParameters: WorkerParameters,
        coroutineDispatcher: CoroutineDispatcher,
        private val prefs: Prefs
) : CoroutineWorker(context, workerParameters, coroutineDispatcher) {

    override suspend fun work(): Payload = Payload(try {
        prefs.log(LOG_TAG, "Starting")
        Thread.sleep(5000)
        prefs.log(LOG_TAG, "Success")
        SUCCESS
    } catch (t: Throwable) {
        prefs.log(LOG_TAG, "Failure")
        FAILURE
    })

}

private const val LOG_TAG = "CharNetWorker"
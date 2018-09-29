package com.satis.app.work

import android.content.Context
import androidx.work.Worker
import androidx.work.Worker.Result.FAILURE
import androidx.work.Worker.Result.SUCCESS
import androidx.work.WorkerParameters
import com.satis.app.common.Prefs

class NetworkWorker(
        context: Context,
        workerParameters: WorkerParameters,
        private val prefs: Prefs
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        prefs.log(LOG_TAG, "Starting")
        return try {
            Thread.sleep(5000)
            prefs.log(LOG_TAG, "Success")
            SUCCESS
        } catch (t: Throwable) {
            prefs.log(LOG_TAG, "Failure")
            FAILURE
        }
    }
}

private val LOG_TAG = "NetWorker"
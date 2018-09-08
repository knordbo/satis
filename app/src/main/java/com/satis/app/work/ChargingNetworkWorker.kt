package com.satis.app.work

import androidx.work.Worker
import androidx.work.Worker.Result.FAILURE
import androidx.work.Worker.Result.SUCCESS
import com.satis.app.appComponent

class ChargingNetworkWorker : Worker() {
    override fun doWork(): Result {
        val prefs = applicationContext.appComponent().prefs()
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

private val LOG_TAG = "CharNetWorker"
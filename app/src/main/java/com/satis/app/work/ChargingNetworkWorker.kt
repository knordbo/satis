package com.satis.app.work

import android.content.Context
import androidx.work.ListenableWorker.Result.FAILURE
import androidx.work.ListenableWorker.Result.SUCCESS
import androidx.work.WorkerParameters
import com.satis.app.common.Prefs
import io.reactivex.Scheduler
import io.reactivex.Single

class ChargingNetworkWorker(
        context: Context,
        workerParameters: WorkerParameters,
        private val prefs: Prefs,
        private val ioScheduler: Scheduler
) : RxWorker(context, workerParameters) {

    override fun work(): Single<Payload> = Single.fromCallable {
        prefs.log(LOG_TAG, "Starting")
        return@fromCallable Payload(try {
            Thread.sleep(5000)
            prefs.log(LOG_TAG, "Success")
            SUCCESS
        } catch (t: Throwable) {
            prefs.log(LOG_TAG, "Failure")
            FAILURE
        })
    }.subscribeOn(ioScheduler)

}

private val LOG_TAG = "CharNetWorker"
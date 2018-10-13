package com.satis.app.work

import android.content.Context
import androidx.work.ListenableWorker.Result.FAILURE
import androidx.work.ListenableWorker.Result.SUCCESS
import androidx.work.WorkerParameters
import com.satis.app.common.Prefs
import io.reactivex.Scheduler
import io.reactivex.Single

class NetworkWorker(
        context: Context,
        workerParameters: WorkerParameters,
        private val prefs: Prefs,
        private val ioScheduler: Scheduler
) : RxWorker(context, workerParameters) {

    override fun work(): Single<Payload> = Single.fromCallable {
        Payload(try {
            prefs.log(LOG_TAG, "Starting")
            Thread.sleep(5000)
            prefs.log(LOG_TAG, "Success")
            SUCCESS
        } catch (t: Throwable) {
            prefs.log(LOG_TAG, "Failure")
            FAILURE
        })
    }.subscribeOn(ioScheduler)

}

private val LOG_TAG = "NetWorker"
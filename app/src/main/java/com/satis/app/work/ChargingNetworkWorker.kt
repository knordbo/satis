package com.satis.app.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.ListenableWorker.Result.FAILURE
import androidx.work.ListenableWorker.Result.SUCCESS
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import com.satis.app.common.Prefs
import io.reactivex.Scheduler
import io.reactivex.Single

class ChargingNetworkWorker(
        context: Context,
        workerParameters: WorkerParameters,
        private val prefs: Prefs,
        private val ioScheduler: Scheduler
) : ListenableWorker(context, workerParameters) {
    override fun onStartWork(): ListenableFuture<Payload> {
        val future = SettableFuture.create<Payload>()
        Single.fromCallable {
            doWork()
        }
                .subscribeOn(ioScheduler)
                .subscribe { result ->
                    future.set(Payload(result))
                }
        return future
    }

    private fun doWork(): Result {
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
package com.satis.app.work

import android.content.Context
import androidx.concurrent.futures.ResolvableFuture
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class CoroutineWorker(
        context: Context,
        workerParameters: WorkerParameters,
        private val coroutineContext: CoroutineContext
) : ListenableWorker(context, workerParameters) {

    private var job: Job? = null

    override fun onStartWork(): ListenableFuture<Payload> {
        val future = ResolvableFuture.create<Payload>()
        future.addListener({
            if (future.isCancelled) {
                job?.cancel()
            }
        }, { command ->
            command?.run()
        })

        job = GlobalScope.launch(coroutineContext) {
            try {
                future.set(work())
            } catch (t: Throwable) {
                future.setException(t)
            }
        }
        return future
    }

    override fun onStopped(cancelled: Boolean) {
        super.onStopped(cancelled)
        job?.cancel()
    }

    abstract suspend fun work(): Payload

}
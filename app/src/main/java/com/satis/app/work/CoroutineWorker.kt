package com.satis.app.work

import android.content.Context
import androidx.concurrent.futures.ResolvableFuture
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class CoroutineWorker(
        context: Context,
        workerParameters: WorkerParameters,
        private val coroutineDispatcher: CoroutineDispatcher
) : ListenableWorker(context, workerParameters), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + coroutineDispatcher

    override fun onStartWork(): ListenableFuture<Payload> {
        val future = ResolvableFuture.create<Payload>()
        future.addListener({
            if (future.isCancelled) {
                job.cancel()
            }
        }, { command ->
            command?.run()
        })

        launch {
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
        job.cancel()
    }

    abstract suspend fun work(): Payload

}
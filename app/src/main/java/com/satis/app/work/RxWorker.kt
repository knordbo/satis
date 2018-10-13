package com.satis.app.work

import android.content.Context
import androidx.concurrent.futures.ResolvableFuture
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class RxWorker(context: Context, workerParameters: WorkerParameters) : ListenableWorker(context, workerParameters) {

    private var disposable: Disposable? = null

    abstract fun work(): Single<Payload>

    override fun onStartWork(): ListenableFuture<Payload> {
        val future = ResolvableFuture.create<Payload>()
        future.addListener({
            if (future.isCancelled) {
                disposable?.dispose()
            }
        }, { command ->
            command?.run()
        })
        disposable = work().subscribe({
            future.set(it)
        }, {
            future.setException(it)
        })
        return future
    }

    override fun onStopped(cancelled: Boolean) {
        super.onStopped(cancelled)
        disposable?.dispose()
    }

}
package com.satis.app.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.satis.app.utils.system.forNameAsSubclass
import javax.inject.Inject
import javax.inject.Provider

class InjectingWorkerFactory @Inject constructor(
  private val creators: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
) : WorkerFactory() {

  override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
    val creator = creators[forNameAsSubclass<ListenableWorker>(workerClassName)]
    return creator?.get()?.create(appContext, workerParameters)
  }

}
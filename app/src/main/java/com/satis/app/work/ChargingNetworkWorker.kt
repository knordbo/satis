package com.satis.app.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.satis.app.common.annotations.Io
import com.satis.app.common.logging.Logger
import com.satis.app.utils.lifecycle.isAppForegroundString
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ChargingNetworkWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    @Io private val io: CoroutineContext,
    private val logger: Logger
) : CoroutineWorker(context, workerParameters) {

  override suspend fun doWork(): Result = withContext(io) {
    try {
      logger.log(LOG_TAG, "Starting in $isAppForegroundString")
      logger.log(LOG_TAG, "Success")
      Result.success()
    } catch (t: Throwable) {
      logger.log(LOG_TAG, "Failure")
      Result.failure()
    }
  }

  @AssistedInject.Factory
  interface Factory : ChildWorkerFactory

}

private const val LOG_TAG = "CharNetWorker"
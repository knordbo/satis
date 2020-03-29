package com.satis.app.work

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.NetworkType.UNMETERED
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.satis.app.common.logging.Logger
import com.satis.app.feature.images.work.ImageWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkSchedulerImpl @Inject constructor(
    private val logger: Logger,
    private val workManager: WorkManager
) : WorkScheduler {

  override fun schedule() {
    logger.log(LOG_TAG, "Scheduling work")
    recurringImageFetchJob()
    logger.log(LOG_TAG, "Done scheduling work")
  }

  private fun recurringImageFetchJob() {
    val name = "image_fetch_job"

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(UNMETERED)
        .setRequiresCharging(true)
        .build()

    val work = PeriodicWorkRequestBuilder<ImageWorker>(24, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()

    workManager.enqueueUniquePeriodicWork(name, KEEP, work)
  }

}

private const val LOG_TAG = "WorkSchedulerImpl"
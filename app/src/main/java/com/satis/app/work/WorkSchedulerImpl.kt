package com.satis.app.work

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType.UNMETERED
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.satis.app.common.logging.Logger
import com.satis.app.feature.images.work.ImageWorker
import com.satis.app.feature.images.work.PERIODIC
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkSchedulerImpl @Inject constructor(
  private val logger: Logger,
  private val workManager: WorkManager,
) : WorkScheduler {

  override fun schedulePeriodic() {
    logger.log(LOG_TAG, "Scheduling work")
    triggerPeriodicImageFetch()
    logger.log(LOG_TAG, "Done scheduling work")
  }

  override fun scheduleImageFetch() {
    logger.log(LOG_TAG, "Scheduling work on thread: ${Thread.currentThread()}")
    workManager.enqueueUniqueWork(
      IMAGE_FETCH_ONE_TIME_WORK_NAME,
      ExistingWorkPolicy.APPEND,
      OneTimeWorkRequestBuilder<ImageWorker>().build(),
    )
  }

  private fun triggerPeriodicImageFetch() {
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(UNMETERED)
      .setRequiresCharging(true)
      .build()

    val work = PeriodicWorkRequestBuilder<ImageWorker>(24, TimeUnit.HOURS)
      .setConstraints(constraints)
      .addTag(PERIODIC)
      .build()

    workManager.enqueueUniquePeriodicWork(IMAGE_FETCH_PERIODIC_WORK_NAME, KEEP, work)
  }

}

private const val LOG_TAG = "WorkSchedulerImpl"
private const val IMAGE_FETCH_PERIODIC_WORK_NAME = "image_fetch_periodic"
private const val IMAGE_FETCH_ONE_TIME_WORK_NAME = "image_fetch_one_time"
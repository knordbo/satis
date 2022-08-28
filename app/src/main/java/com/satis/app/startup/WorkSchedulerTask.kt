package com.satis.app.startup

import com.satis.app.work.WorkScheduler
import javax.inject.Inject

class WorkSchedulerTask @Inject constructor(
  private val workScheduler: WorkScheduler
) : StartupTask {
  override suspend fun execute() {
    workScheduler.schedulePeriodic()
  }
}
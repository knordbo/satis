package com.satis.app.work

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.NetworkType.CONNECTED
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.satis.app.common.Prefs
import java.util.concurrent.TimeUnit

class WorkScheduler(private val prefs: Prefs, private val workManager: WorkManager) {

    fun schedule() {
        prefs.log(LOG_TAG, "Scheduling work")
        recurringNetworkJob()
        recurringChargingNetworkJob()
        prefs.log(LOG_TAG, "Done scheduling work")
    }

    private fun recurringNetworkJob() {
        val name = "network_job"

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(CONNECTED)
                .build()

        val work = PeriodicWorkRequest.Builder(NetworkWorker::class.java, 5, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        workManager.enqueueUniquePeriodicWork(name, KEEP, work)
    }

    private fun recurringChargingNetworkJob() {
        val name = "charging_network_job"

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(CONNECTED)
                .setRequiresCharging(true)
                .build()

        val work = PeriodicWorkRequest.Builder(ChargingNetworkWorker::class.java, 24, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        workManager.enqueueUniquePeriodicWork(name, KEEP, work)
    }

}

private const val LOG_TAG = "WorkScheduler"
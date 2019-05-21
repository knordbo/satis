package com.satis.app

import android.app.Activity
import android.content.IntentSender
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.satis.app.common.logging.Logger

class ImmediateAppUpdater(
        private val activity: Activity,
        private val appUpdateManager: AppUpdateManager,
        private val logger: Logger
) {

    fun startAppUpdateIfNeeded() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() in listOf(UpdateAvailability.UPDATE_AVAILABLE, UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    logger.log(LOG_TAG, "Starting app update")
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            activity,
                            IMMEDIATE_IN_APP_UPDATE
                    )
                } catch (e: IntentSender.SendIntentException) {
                    logger.log(LOG_TAG, "SendIntentException: $e")
                }
            }
        }
    }
}

private const val LOG_TAG = "ImmediateAppUpdater"
private const val IMMEDIATE_IN_APP_UPDATE = 1
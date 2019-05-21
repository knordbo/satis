package com.satis.app

import android.app.Activity
import android.app.Activity.RESULT_OK
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class ImmediateAppUpdater(
        private val activity: Activity,
        private val appUpdateManager: AppUpdateManager
) {
    fun onResume() {
        startUpdateIfNeeded()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == IMMEDIATE_IN_APP_UPDATE && resultCode != RESULT_OK) {
            startUpdateIfNeeded()
        }
    }

    private fun startUpdateIfNeeded() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() in listOf(UpdateAvailability.UPDATE_AVAILABLE, UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        activity,
                        IMMEDIATE_IN_APP_UPDATE
                )
            }
        }
    }
}

private const val IMMEDIATE_IN_APP_UPDATE = 1
package com.satis.app.common.updater

import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import com.satis.app.common.logging.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ImmediateAppUpdater @AssistedInject constructor(
  @Assisted private val activity: AppCompatActivity,
  private val appUpdateManager: AppUpdateManager,
  private val logger: Logger,
) {

  fun startAppUpdateIfNeeded(initialCall: Boolean) {
    appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
      val shouldStartUpdate = initialCall && appUpdateInfo.updateAvailability() == UPDATE_AVAILABLE
      val shouldContinueUpdate =
        appUpdateInfo.updateAvailability() == DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
      if ((shouldStartUpdate || shouldContinueUpdate) && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
        try {
          logger.log(LOG_TAG, "Starting app update")
          appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            IMMEDIATE,
            activity,
            IMMEDIATE_IN_APP_UPDATE
          )
        } catch (e: IntentSender.SendIntentException) {
          logger.log(LOG_TAG, "SendIntentException: $e")
        }
      }
    }
  }

  interface Factory {
    fun create(activity: AppCompatActivity): ImmediateAppUpdater
  }

  @AssistedFactory
  interface FactoryImpl : Factory
}

private const val LOG_TAG = "ImmediateAppUpdater"
private const val IMMEDIATE_IN_APP_UPDATE = 1
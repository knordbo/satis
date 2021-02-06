package com.satis.app.feature.notifications.system

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.data.PushNotification
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationService @Inject constructor(
  private val notificationRepository: NotificationRepository,
  private val notificationHandler: PushNotificationHandler,
  private val logger: PersistedLogger,
  private val json: Json,
): FirebaseMessagingService() {

  override fun onMessageReceived(remoteMessage: RemoteMessage) {
    GlobalScope.launch {
      try {
        val jsonStr = remoteMessage.data["json"]!!
        val pushNotification: PushNotification = json.decodeFromString(jsonStr)
        logger.log(LOG_TAG, "Received notification: ${pushNotification.id}")
        notificationHandler.handle(pushNotification)
      } catch (t: Throwable) {
        logger.log(LOG_TAG, "Received broken notification: $t")
      }
    }
  }

  override fun onNewToken(token: String) {
    logger.log(LOG_TAG, "onNewToken $token")
    GlobalScope.launch {
      notificationRepository.updateToken(token)
    }
  }
}

const val LOG_TAG = "NotificationService"

package com.satis.app.feature.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.data.toPushNotification
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationService @Inject constructor(
  private val notificationRepository: NotificationRepository,
  private val notificationHandler: PushNotificationHandler,
  private val logger: PersistedLogger,
): FirebaseMessagingService() {

  override fun onMessageReceived(remoteMessage: RemoteMessage) {
    val pushNotification = remoteMessage.toPushNotification()
    if (pushNotification != null) {
      logger.log(LOG_TAG, "Received notification: $pushNotification")
      notificationHandler.handle(pushNotification)
    } else {
      logger.log(LOG_TAG, "Received broken notification")
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

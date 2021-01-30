package com.satis.app.feature.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.feature.notifications.data.NotificationRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationService @Inject constructor(
  private val notificationRepository: NotificationRepository,
  private val logger: PersistedLogger,
): FirebaseMessagingService() {

  override fun onMessageReceived(remoteMessage: RemoteMessage) {
    val data = remoteMessage.data
    val notification = remoteMessage.notification
    val message = when {
      data.isNotEmpty() -> data.toString()
      notification != null -> notification.title + notification.body
      else -> ""
    }
    logger.log(LOG_TAG, "Received $message")
  }

  override fun onNewToken(token: String) {
    logger.log(LOG_TAG, "onNewToken $token")
    GlobalScope.launch {
      notificationRepository.updateToken(token)
    }
  }
}

const val LOG_TAG = "NotificationService"
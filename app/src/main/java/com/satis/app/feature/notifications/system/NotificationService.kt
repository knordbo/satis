package com.satis.app.feature.notifications.system

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.satis.app.common.account.AccountId
import com.satis.app.common.annotations.MostRecentCurrentAccount
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.di.account.AccountProvider
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.data.PushNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {

  @Inject lateinit var notificationRepositoryProvider: AccountProvider<NotificationRepository>
  @Inject lateinit var pushNotificationHandlerProvider: AccountProvider<PushNotificationHandler>
  @Inject lateinit var logger: PersistedLogger
  @Inject lateinit var json: Json
  @Inject @MostRecentCurrentAccount lateinit var mostRecentCurrentAccountId: Provider<AccountId>

  private val notificationRepository: NotificationRepository
    get() = notificationRepositoryProvider.get(mostRecentCurrentAccountId.get())

  private val pushNotificationHandler: PushNotificationHandler
    get() = pushNotificationHandlerProvider.get(mostRecentCurrentAccountId.get())

  override fun onMessageReceived(remoteMessage: RemoteMessage) {
    GlobalScope.launch {
      try {
        val jsonStr = remoteMessage.data["json"]!!
        val pushNotification: PushNotification = json.decodeFromString(jsonStr)
        logger.log(LOG_TAG, "Received notification: ${pushNotification.id}")
        pushNotificationHandler.handle(pushNotification)
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

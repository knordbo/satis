package com.satis.app.feature.notifications

import com.satis.app.feature.notifications.data.PushNotification

interface PushNotificationHandler {
  suspend fun handle(pushNotification: PushNotification)
}

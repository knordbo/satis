package com.satis.app.feature.notifications

import com.satis.app.feature.notifications.data.PushNotification

interface PushNotificationHandler {
  fun handle(pushNotification: PushNotification)
}

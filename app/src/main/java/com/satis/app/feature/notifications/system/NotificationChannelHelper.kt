package com.satis.app.feature.notifications.system

import com.satis.app.feature.notifications.data.PushNotification

interface NotificationChannelHelper {
  fun getChannelId(pushNotification: PushNotification): String
}

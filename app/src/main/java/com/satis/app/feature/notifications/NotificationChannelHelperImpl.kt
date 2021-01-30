package com.satis.app.feature.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.satis.app.R
import com.satis.app.feature.notifications.data.PushNotification
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationChannelHelperImpl @Inject constructor(
  private val context: Context,
  private val notificationManager: NotificationManagerCompat,
): NotificationChannelHelper {

  override fun getChannelId(pushNotification: PushNotification): String {
    val channel = if (pushNotification.isImportant) {
      NotificationChannel(
        "1",
        context.getString(R.string.notification_channel_high_priority),
        NotificationManager.IMPORTANCE_HIGH
      )
    } else {
      NotificationChannel(
        "2",
        context.getString(R.string.notification_channel_regular_priority),
        NotificationManager.IMPORTANCE_DEFAULT
      )
    }
    notificationManager.createNotificationChannel(channel)
    return channel.id
  }
}


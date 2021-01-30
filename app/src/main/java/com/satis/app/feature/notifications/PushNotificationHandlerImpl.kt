package com.satis.app.feature.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.satis.app.feature.notifications.data.PushNotification
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PushNotificationHandlerImpl @Inject constructor(
  private val context: Context,
  private val notificationManager: NotificationManagerCompat,
  private val notificationChannelHelper: NotificationChannelHelper
): PushNotificationHandler {

  override fun handle(pushNotification: PushNotification) {
    if (!pushNotification.isSilent) {
      pushNotification.showNotification()
    }
  }

  private fun PushNotification.showNotification() {
    val pendingIntent = if (url != null) {
      PendingIntent.getActivity(context, 0, Intent(Intent.ACTION_VIEW, Uri.parse(url)), 0)
    } else {
      null
    }

    val notification = NotificationCompat.Builder(context, DEFAULT_CHANNEL)
      .setContentTitle(title)
      .setContentText(body)
      .setAutoCancel(true)
      .setContentIntent(pendingIntent)
      .setChannelId(notificationChannelHelper.getChannelId(this))
      .build()

    notificationManager.notify(id, 0, notification)
  }
}

private const val DEFAULT_CHANNEL = "default"

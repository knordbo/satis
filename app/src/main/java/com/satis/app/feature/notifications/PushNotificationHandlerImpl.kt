package com.satis.app.feature.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.satis.app.MainActivity
import com.satis.app.R
import com.satis.app.feature.notifications.data.PushNotification
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PushNotificationHandlerImpl @Inject constructor(
  private val context: Context,
  private val notificationManager: NotificationManagerCompat,
  private val notificationChannelHelper: NotificationChannelHelper,
) : PushNotificationHandler {

  override suspend fun handle(pushNotification: PushNotification) {
    if (!pushNotification.isSilent) {
      pushNotification.showNotification()
    }
  }

  private suspend fun PushNotification.showNotification() {
    val intent = if (url != null) {
      Intent(Intent.ACTION_VIEW, Uri.parse(url))
    } else {
      MainActivity.getIntent(context)
    }
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

    val icon = if (icon != null) {
      val request = ImageRequest.Builder(context)
        .transformations(if (icon.useCircleCrop) listOf(CircleCropTransformation()) else emptyList())
        .data(icon.url)
        .build()
      context.imageLoader.execute(request).drawable?.toBitmap()
    } else {
      null
    }

    val notification = NotificationCompat.Builder(context, DEFAULT_CHANNEL)
      .setContentTitle(title)
      .setContentText(body)
      .setAutoCancel(true)
      .setContentIntent(pendingIntent)
      .setSmallIcon(R.drawable.ic_notification)
      .setLargeIcon(icon)
      .setChannelId(notificationChannelHelper.getChannelId(this))
      .build()

    notificationManager.notify(id, 0, notification)
  }
}

private const val DEFAULT_CHANNEL = "default"

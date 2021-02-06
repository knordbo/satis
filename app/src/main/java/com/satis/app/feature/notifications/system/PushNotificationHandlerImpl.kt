package com.satis.app.feature.notifications.system

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.satis.app.MainActivity
import com.satis.app.R
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.data.PushNotification
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PushNotificationHandlerImpl @Inject constructor(
  private val context: Context,
  private val notificationManager: NotificationManagerCompat,
  private val notificationChannelHelper: NotificationChannelHelper,
  private val notificationRepository: NotificationRepository,
) : PushNotificationHandler {

  override suspend fun handle(pushNotification: PushNotification) {
    if (!pushNotification.isSilent) {
      pushNotification.showNotification()
      notificationRepository.insertNotification(pushNotification)
    }
  }

  private suspend fun PushNotification.showNotification() {
    val notification = NotificationCompat.Builder(context, DEFAULT_CHANNEL)
      .setContentTitle(title)
      .setContentText(body)
      .setStyle(NotificationCompat.BigTextStyle().bigText(body))
      .setAutoCancel(true)
      .setContentIntent(getPendingIntent())
      .setSmallIcon(R.drawable.ic_notification)
      .setLargeIcon(getIcon())
      .setChannelId(notificationChannelHelper.getChannelId(this))
      .build()

    notificationManager.notify(id, 0, notification)
  }

  private fun PushNotification.getPendingIntent(): PendingIntent? {
    val intent = if (url != null) {
      Intent(Intent.ACTION_VIEW, Uri.parse(url))
    } else {
      MainActivity.getIntent(context)
    }
    return PendingIntent.getActivity(context, 0, intent, 0)
  }

  private suspend fun PushNotification.getIcon(): Bitmap? = if (icon != null) {
    withTimeoutOrNull(MAX_TIME_SPENT_DOWNLOADING_LARGE_ICON) {
      val request = ImageRequest.Builder(context)
        .transformations(if (icon.useCircleCrop) listOf(CircleCropTransformation()) else emptyList())
        .size(LARGE_ICON_SIZE)
        .data(icon.url)
        .build()
      context.imageLoader.execute(request).drawable?.toBitmap()
    }
  } else {
    null
  }
}

private const val DEFAULT_CHANNEL = "default"
private const val LARGE_ICON_SIZE = 196
private const val MAX_TIME_SPENT_DOWNLOADING_LARGE_ICON = 5000L

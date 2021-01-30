package com.satis.app.feature.notifications.data

import com.google.firebase.messaging.RemoteMessage

data class PushNotification(
  val id: String,
  val title: String,
  val body: String,
  val url: String?,
  val isSilent: Boolean,
  val isImportant: Boolean,
)

fun RemoteMessage.toPushNotification(): PushNotification? = try {
  PushNotification(
    id = data["id"]!!,
    title = data["title"]!!,
    body = data["body"]!!,
    url = data["url"]!!,
    isSilent = data["is_silent"]!!.toBoolean(),
    isImportant = data["is_important"]!!.toBoolean(),
  )
} catch (t: Throwable) {
  null
}

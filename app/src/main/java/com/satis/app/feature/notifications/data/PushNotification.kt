package com.satis.app.feature.notifications.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PushNotification(
  @SerialName("id") val id: String,
  @SerialName("title") val title: String,
  @SerialName("body") val body: String,
  @SerialName("icon") val icon: PushIcon?,
  @SerialName("url") val url: String?,
  @SerialName("created_at") val createdAt: Long,
  @SerialName("is_silent") val isSilent: Boolean,
  @SerialName("is_important") val isImportant: Boolean,
)

@Serializable
data class PushIcon(
  @SerialName("url") val url: String,
  @SerialName("use_circle_crop") val useCircleCrop: Boolean
)

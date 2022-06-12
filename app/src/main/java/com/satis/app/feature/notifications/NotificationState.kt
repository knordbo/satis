package com.satis.app.feature.notifications

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationState(
  val notifications: List<Notification> = emptyList(),
) : Parcelable

@Parcelize
data class Notification(
  val id: String,
  val title: String,
  val body: String,
  val icon: Icon?,
  val url: String?,
  val createdAt: Long,
  val isSilent: Boolean,
  val isImportant: Boolean,
) : Parcelable

@Parcelize
data class Icon(
  val url: String,
  val useCircleCrop: Boolean,
) : Parcelable

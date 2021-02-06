package com.satis.app.feature.notifications.data

import com.satis.app.feature.notifications.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
  fun getToken(): Flow<String>
  suspend fun updateToken(token: String)
  suspend fun getNotifications(offset: Int, pageSize: Int = 20): List<Notification>
  fun streamNotifications(): Flow<List<Notification>>
  suspend fun insertNotification(pushNotification: PushNotification)
  suspend fun deleteAll()
  suspend fun notificationSeen(id: String)
}

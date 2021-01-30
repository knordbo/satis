package com.satis.app.feature.notifications.data

import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
  fun getToken(): Flow<String>
  suspend fun updateToken(token: String)
}

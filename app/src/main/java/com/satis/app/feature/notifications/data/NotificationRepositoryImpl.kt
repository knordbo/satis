package com.satis.app.feature.notifications.data

import androidx.core.app.NotificationManagerCompat
import com.satis.app.common.annotations.Io
import com.satis.app.common.prefs.Prefs
import com.satis.app.feature.notifications.Icon
import com.satis.app.feature.notifications.Notification
import com.satis.app.feature.notifications.data.db.NotificationEntity
import com.satis.app.feature.notifications.data.db.NotificationQueries
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class NotificationRepositoryImpl @Inject constructor(
  private val prefs: Prefs,
  @Io private val io: CoroutineContext,
  private val notificationQueries: NotificationQueries,
  private val notificationManager: NotificationManagerCompat,
) : NotificationRepository {

  private val _tokenFlow: MutableStateFlow<String> = MutableStateFlow("")
  private val tokenFlow: Flow<String> = _tokenFlow.asStateFlow()

  init {
    updateFlow()
  }

  override fun getToken(): Flow<String> = tokenFlow

  override suspend fun updateToken(token: String) {
    prefs.notificationToken = token
    updateFlow()
  }

  override suspend fun getNotifications(
    offset: Int,
    pageSize: Int,
  ): List<Notification> = withContext(io) {
    notificationQueries.notifications(
      offset = offset.toLong(),
      limit = pageSize.toLong()
    ).toNotifications()
  }

  override fun streamNotifications(): Flow<List<Notification>> = notificationQueries.streamNotifications()
    .asFlow()
    .map { notifications ->
      notifications.toNotifications()
    }
    .flowOn(io)

  override suspend fun insertNotification(pushNotification: PushNotification) = withContext(io) {
    notificationQueries.insertNotification(
      id = pushNotification.id,
      title = pushNotification.title,
      body = pushNotification.body,
      iconUrl = pushNotification.icon?.url,
      iconUseCircleCrop = pushNotification.icon?.useCircleCrop,
      url = pushNotification.url,
      createdAt = pushNotification.createdAt,
      isSilent = pushNotification.isSilent,
      isImportant = pushNotification.isImportant,
    )
  }

  override suspend fun deleteAll() = withContext(io) {
    notificationQueries.deleteAll()
  }

  override suspend fun notificationSeen(id: String) {
    notificationManager.cancel(id, 0)
  }

  private fun updateFlow() {
    val notificationToken = prefs.notificationToken
    if (notificationToken != null) {
      _tokenFlow.tryEmit(notificationToken)
    }
  }

  private fun Query<NotificationEntity>.toNotifications() =
    executeAsList().map { notification ->
      notification.toNotification()
    }

  private fun NotificationEntity.toNotification(): Notification = Notification(
    id = id,
    title = title,
    body = body,
    icon = if (iconUrl != null) {
      Icon(iconUrl, iconUseCircleCrop!!)
    } else {
      null
    },
    url = url,
    createdAt = createdAt,
    isSilent = isSilent,
    isImportant = isImportant,
  )
}

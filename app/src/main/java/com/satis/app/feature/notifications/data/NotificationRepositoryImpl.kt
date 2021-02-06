package com.satis.app.feature.notifications.data

import com.satis.app.common.annotations.Io
import com.satis.app.common.prefs.Prefs
import com.satis.app.feature.notifications.Icon
import com.satis.app.feature.notifications.Notification
import com.satis.app.feature.notifications.data.db.NotificationQueries
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

  override suspend fun getNotifications(offset: Int, pageSize: Int): List<Notification> = withContext(io) {
    notificationQueries.notifications(offset = offset.toLong(), limit = pageSize.toLong())
      .executeAsList().map { notificationEntity ->
        Notification(
          id = notificationEntity.id,
          title = notificationEntity.title,
          body = notificationEntity.body,
          icon = if (notificationEntity.iconUrl != null) {
            Icon(notificationEntity.iconUrl, notificationEntity.iconUseCircleCrop!!)
          } else {
            null
          },
          url = notificationEntity.url,
          createdAt = notificationEntity.createdAt,
          isSilent = notificationEntity.isSilent,
          isImportant = notificationEntity.isImportant,
        )
      }
  }

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

  private fun updateFlow() {
    val notificationToken = prefs.notificationToken
    if (notificationToken != null) {
      _tokenFlow.tryEmit(notificationToken)
    }
  }
}

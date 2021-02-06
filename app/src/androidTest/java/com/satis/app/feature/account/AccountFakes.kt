package com.satis.app.feature.account

import com.satis.app.common.logging.LogEntry
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.Theme
import com.satis.app.common.prefs.UserId
import com.satis.app.feature.account.appinfo.AppInfo
import com.satis.app.feature.account.appinfo.AppInfoRetriever
import com.satis.app.feature.notifications.Notification
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.feature.notifications.data.PushNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun createAccountViewModel(initialState: AccountState = AccountState()): AccountViewModel {
  return AccountViewModel(
    initialState = initialState,
    logger = createPersistedLogger(),
    appInfoRetriever = createAppInfoRetriever(),
    prefs = createPrefs(),
    notificationRepository = createNotificationRepository(),
  )
}

private fun createPrefs(): Prefs {
  return object : Prefs {
    override val userId: UserId = UserId("userId")
    override var theme: Theme = Theme.SYSTEM
    override var notificationToken: String? = "my_token"
  }
}

private fun createAppInfoRetriever(): AppInfoRetriever {
  return object : AppInfoRetriever {
    override suspend fun getAppInfo(): AppInfo = AppInfo(
      versionCode = 1,
      buildTime = 1
    )
  }
}

private fun createPersistedLogger(): PersistedLogger {
  return object : PersistedLogger {
    override fun log(tag: String, message: String) = Unit
    override fun streamLogs(): Flow<List<LogEntry>> = flowOf(emptyList())
    override suspend fun searchLogs(query: String): List<LogEntry> = emptyList()
  }
}

private fun createNotificationRepository(): NotificationRepository {
  return object : NotificationRepository {
    override fun getToken(): Flow<String> = flowOf("my_token")
    override suspend fun updateToken(token: String) = Unit
    override suspend fun getNotifications(offset: Int, pageSize: Int): List<Notification> = emptyList()
    override fun streamNotifications(): Flow<List<Notification>> = flowOf()
    override suspend fun insertNotification(pushNotification: PushNotification) = Unit
    override suspend fun deleteAll() = Unit
    override suspend fun notificationSeen(id: String) = Unit
  }
}
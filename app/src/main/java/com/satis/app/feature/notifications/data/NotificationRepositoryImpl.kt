package com.satis.app.feature.notifications.data

import com.satis.app.common.prefs.Prefs
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
  private val prefs: Prefs,
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

  private fun updateFlow() {
    val notificationToken = prefs.notificationToken
    if (notificationToken != null) {
      _tokenFlow.tryEmit(notificationToken)
    }
  }
}

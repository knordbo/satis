package com.satis.app.feature.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.Theme
import com.satis.app.common.prefs.apply
import com.satis.app.feature.account.appinfo.AppInfoRetriever
import com.satis.app.feature.notifications.data.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class AccountViewModel @Inject constructor(
  private val logger: PersistedLogger,
  private val appInfoRetriever: AppInfoRetriever,
  private val prefs: Prefs,
  private val notificationRepository: NotificationRepository,
) : ViewModel(), CoroutineScope {

  override val coroutineContext: CoroutineContext
    get() = viewModelScope.coroutineContext

  private val _state: MutableStateFlow<AccountState> = MutableStateFlow(value = AccountState())
  val state: StateFlow<AccountState> = _state.asStateFlow()

  fun load() {
    getAccountState()
    streamLogs()
    streamNotificationToken()
  }

  fun setTheme(theme: Theme) {
    prefs.theme = theme
    theme.apply()
  }

  private fun streamLogs() {
    launch {
      logger.streamLogs().collect { logs ->
        setState {
          copy(logs = logs)
        }
      }
    }
  }

  private fun streamNotificationToken() {
    launch {
      notificationRepository.getToken().collect { token ->
        setState {
          copy(notificationToken = token)
        }
      }
    }
  }

  private fun getAccountState() {
    launch {
      val appInfo = appInfoRetriever.getAppInfo()
      setState {
        copy(buildData = BuildData(
          versionNum = appInfo.versionCode,
          buildTime = appInfo.buildTime
        ))
      }
    }
  }

  private fun setState(update: AccountState.() -> AccountState) {
    _state.value = _state.value.update()
  }
}
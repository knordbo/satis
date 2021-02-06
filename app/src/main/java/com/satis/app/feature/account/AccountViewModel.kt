package com.satis.app.feature.account

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.Theme
import com.satis.app.common.prefs.apply
import com.satis.app.feature.account.appinfo.AppInfoRetriever
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.utils.coroutines.BaseViewModel
import com.satis.app.utils.coroutines.viewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AccountViewModel @AssistedInject constructor(
  @Assisted initialState: AccountState,
  private val logger: PersistedLogger,
  private val appInfoRetriever: AppInfoRetriever,
  private val prefs: Prefs,
  private val notificationRepository: NotificationRepository,
) : BaseViewModel<AccountState>(
  initialState = initialState
) {

  init {
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

  @AssistedInject.Factory
  interface Factory {
    fun createAccountViewModel(initialState: AccountState): AccountViewModel
  }

  companion object : MvRxViewModelFactory<AccountViewModel, AccountState> {
    override fun create(viewModelContext: ViewModelContext, state: AccountState): AccountViewModel {
      return viewModelContext.viewModelFactory<Factory>().createAccountViewModel(state)
    }
  }
}
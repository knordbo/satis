package com.satis.app.feature.account

import com.satis.app.common.logging.LogEntry
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.common.navigation.NavigationReselectionImpl
import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.Theme
import com.satis.app.common.prefs.UserId
import com.satis.app.feature.account.appinfo.AppInfo
import com.satis.app.feature.account.appinfo.AppInfoRetriever
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun createAccountFragment(): AccountFragment {
  return AccountFragment(
      viewModelFactory = createAccountViewModelFactory(),
      navigationReselection = createNavigationReselection()
  )
}

private fun createAccountViewModelFactory(): AccountViewModel.Factory {
  return object : AccountViewModel.Factory {
    override fun createAccountViewModel(initialState: AccountState): AccountViewModel {
      return AccountViewModel(
          initialState = initialState,
          logger = createPersistedLogger(),
          appInfoRetriever = createAppInfoRetriever(),
          prefs = createPrefs()
      )
    }
  }
}

private fun createNavigationReselection() = NavigationReselectionImpl()

private fun createPrefs(): Prefs {
  return object : Prefs {
    override val userId: UserId = UserId("userId")
    override var theme: Theme = Theme.SYSTEM
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
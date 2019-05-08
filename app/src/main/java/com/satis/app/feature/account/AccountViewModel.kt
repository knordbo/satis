package com.satis.app.feature.account

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.BuildConfig
import com.satis.app.common.logging.Logger
import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.Theme
import com.satis.app.common.prefs.apply
import com.satis.app.utils.coroutines.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class AccountViewModel(
        initialState: AccountState,
        private val logger: Logger,
        private val prefs: Prefs
) : BaseViewModel<AccountState>(
        initialState = initialState,
        debugMode = BuildConfig.DEBUG
) {

    init {
        logStateChanges()
        getAccountState()
        streamLogs()
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

    private fun getAccountState() {
        setState {
            copy(buildData = BuildData(
                    versionNum = BuildConfig.VERSION_CODE,
                    buildTime = BuildConfig.BUILD_TIME
            ))
        }
    }

    companion object : MvRxViewModelFactory<AccountViewModel, AccountState> {
        override fun create(viewModelContext: ViewModelContext, state: AccountState): AccountViewModel? =
                viewModelContext.activity.get { parametersOf(state) }
    }
}
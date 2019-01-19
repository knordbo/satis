package com.satis.app.feature.account

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.BuildConfig
import com.satis.app.common.logging.Logger
import com.satis.app.utils.coroutines.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class AccountViewModel(
        initialState: AccountState,
        private val logger: Logger
) : BaseViewModel<AccountState>(
        initialState = initialState,
        debugMode = BuildConfig.DEBUG
) {

    init {
        logStateChanges()
        getAccountState()
        streamLogs()
    }

    private fun streamLogs() {
        launch {
            for (logs in logger.streamLogs()) {
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
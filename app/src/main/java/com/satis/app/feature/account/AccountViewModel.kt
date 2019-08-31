package com.satis.app.feature.account

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.BuildConfig
import com.satis.app.common.logging.Logger
import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.Theme
import com.satis.app.common.prefs.apply
import com.satis.app.di.AppModule
import com.satis.app.utils.coroutines.BaseViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AccountViewModel @AssistedInject constructor(
        @Assisted initialState: AccountState,
        private val logger: Logger,
        private val prefs: Prefs
) : BaseViewModel<AccountState>(
        initialState = initialState
) {

    init {
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

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: AccountState): AccountViewModel
    }

    companion object : MvRxViewModelFactory<AccountViewModel, AccountState> {
        override fun create(viewModelContext: ViewModelContext, state: AccountState): AccountViewModel? {
            val fragment: AccountFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.createViewModel(state)
        }
    }
}
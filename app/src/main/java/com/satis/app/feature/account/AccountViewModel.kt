package com.satis.app.feature.account

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.satis.app.BuildConfig
import com.satis.app.common.logging.LogEntry
import com.satis.app.common.logging.Logger
import com.satis.app.utils.coroutines.BaseViewModel
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class AccountViewModel(
        initialState: AccountState,
        private val logger: Logger
) : BaseViewModel<AccountState>(
        initialState = initialState,
        debugMode = BuildConfig.DEBUG
) {

    private val logChanges: Observer<List<LogEntry>> = Observer {
        setState {
            copy(logs = it)
        }
    }

    init {
        logStateChanges()
        getAccountState()
        listenForLogChanges()
    }

    override fun onCleared() {
        super.onCleared()
        logger.getLogs().removeObserver(logChanges)
    }

    private fun getAccountState() {
        setState {
            copy(buildData = BuildData(
                    versionNum = BuildConfig.VERSION_CODE,
                    buildTime = BuildConfig.BUILD_TIME
            ))
        }
    }

    private fun listenForLogChanges() {
        logger.getLogs().observeForever(logChanges)
    }

    companion object : MvRxViewModelFactory<AccountState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: AccountState): BaseMvRxViewModel<AccountState> =
                activity.get<AccountViewModel> { parametersOf(state) }
    }
}
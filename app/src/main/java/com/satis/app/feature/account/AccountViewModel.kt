package com.satis.app.feature.account

import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.satis.app.BuildConfig
import com.satis.app.common.logging.Logger
import com.satis.app.utils.coroutines.BaseViewModel
import io.reactivex.Scheduler
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class AccountViewModel(
        initialState: AccountState,
        private val logger: Logger,
        private val ioScheduler: Scheduler
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
        logger.streamLogs()
                .subscribeOn(ioScheduler)
                .toObservable().execute {
                    copy(logs = it() ?: logs)
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

    companion object : MvRxViewModelFactory<AccountState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: AccountState): BaseMvRxViewModel<AccountState> =
                activity.get<AccountViewModel> { parametersOf(state) }
    }
}
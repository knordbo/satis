package com.satis.app.feature.account

import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.satis.app.BuildConfig
import com.satis.app.common.Prefs
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class AccountViewModel(
        initialState: AccountState,
        private val prefs: Prefs
) : BaseMvRxViewModel<AccountState>(
        initialState = initialState,
        debugMode = BuildConfig.DEBUG
) {
    init {
        logStateChanges()
        getAccountState()
    }

    fun showLog(show: Boolean) {
        setState {
            copy(showLog = show)
        }
    }

    private fun getAccountState() {
        setState {
            copy(data = AccountData(
                    versionNum = BuildConfig.VERSION_CODE,
                    buildTime = BuildConfig.BUILD_TIME,
                    log = prefs.getLog()
            ))
        }
    }

    companion object : MvRxViewModelFactory<AccountState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: AccountState): BaseMvRxViewModel<AccountState> =
                activity.get<AccountViewModel> { parametersOf(state) }
    }
}
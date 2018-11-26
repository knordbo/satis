package com.satis.app.feature.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.viewModel
import com.airbnb.mvrx.withState
import com.satis.app.NavigationViewModel
import com.satis.app.R
import com.satis.app.Tab
import com.satis.app.Tab.ACCOUNT
import com.satis.app.feature.account.ui.LogAdapter
import kotlinx.android.synthetic.main.feature_account.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AccountFragment : BaseMvRxFragment() {

    private val navigationViewModel: NavigationViewModel by activityViewModel()
    private val accountViewModel: AccountViewModel by activityViewModel()
    private val simpleDateFormat: SimpleDateFormat by lazy { SimpleDateFormat("dd.MM.YY HH:mm", Locale.US) }
    private val logAdapter by lazy { LogAdapter() }
    private var previousState: AccountState? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feature_account, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logs.adapter = logAdapter
    }

    override fun invalidate() = withState(accountViewModel, navigationViewModel) { accountState, navigationState ->
        if (accountState.buildData != null) {
            versionNumber.text = resources.getString(R.string.version_info, accountState.buildData.versionNum)
            buildTime.text = resources.getString(R.string.build_time_info, simpleDateFormat.format(Date(accountState.buildData.buildTime)))
            logAdapter.submitList(accountState.logs)
            if (accountState.logs != previousState?.logs) {
                logs.smoothScrollToPosition(0)
            }
            if (navigationState.reselectedTab == ACCOUNT) {
                navigationViewModel.tabReselectedHandled()
                logs.smoothScrollToPosition(0)
            }
        }
        previousState = accountState
    }

}
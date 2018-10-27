package com.satis.app.feature.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.satis.app.R
import com.satis.app.common.fragment.ReselectableFragment
import com.satis.app.common.logging.Logger
import com.satis.app.feature.account.ui.LogAdapter
import kotlinx.android.synthetic.main.feature_account.*
import kotlinx.android.synthetic.main.feature_account.view.*
import org.koin.android.ext.android.get
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AccountFragment : BaseMvRxFragment(), ReselectableFragment {

    private val viewModel: AccountViewModel by activityViewModel()
    private val simpleDateFormat: SimpleDateFormat by lazy { SimpleDateFormat("dd.MM.YY HH:mm", Locale.US) }
    private val logAdapter by lazy { LogAdapter() }
    private var previousState: AccountState? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feature_account, container, false).apply {
                initView()
            }

    override fun invalidate() = withState(viewModel) {
        if (it.buildData != null) {
            versionNumber.text = resources.getString(R.string.version_info, it.buildData.versionNum)
            buildTime.text = resources.getString(R.string.build_time_info, simpleDateFormat.format(Date(it.buildData.buildTime)))
            logAdapter.submitList(it.logs.reversed())
            if (it.logs != previousState?.logs) {
                logs.smoothScrollToPosition(0)
            }
        }
        previousState = it
    }

    override fun onFragmentReselected() {
        get<Logger>().log("TESTING", Math.random().toString())
    }

    private fun View.initView() {
        logs.adapter = logAdapter
    }

}
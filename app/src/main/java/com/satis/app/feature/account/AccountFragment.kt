package com.satis.app.feature.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.satis.app.R
import kotlinx.android.synthetic.main.feature_account.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AccountFragment : BaseMvRxFragment() {

    private val viewModel: AccountViewModel by fragmentViewModel()
    private val simpleDateFormat: SimpleDateFormat by lazy { SimpleDateFormat("dd.MM.YY HH:mm", Locale.US) }
    private var previousState: AccountState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feature_account, container, false)

    override fun invalidate() = withState(viewModel) {
        if (it.data != null) {
            versionNumber.text = resources.getString(R.string.version_info, it.data.versionNum)
            buildTime.text = resources.getString(R.string.build_time_info, simpleDateFormat.format(Date(it.data.buildTime)))

            // Only show dialog if it is not already showing
            if (it.showLog && previousState?.showLog != it.showLog) {
                AlertDialog.Builder(requireContext())
                        .setMessage(it.data.log)
                        .setOnDismissListener {
                            viewModel.showLog(false)
                        }
                        .show()
            }
        }
        previousState = it
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.account_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.log -> {
            viewModel.showLog(true)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
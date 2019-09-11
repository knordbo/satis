package com.satis.app.feature.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.satis.app.common.navigation.NavigationViewModel
import com.satis.app.R
import com.satis.app.common.navigation.Tab.ACCOUNT
import com.satis.app.common.prefs.Theme
import com.satis.app.databinding.FeatureAccountBinding
import com.satis.app.feature.account.ui.LogAdapter
import com.satis.app.utils.view.asyncText
import com.satis.app.utils.view.disableChangeAnimations
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AccountFragment @Inject constructor(
        private val viewModelFactory: AccountViewModel.Factory
): BaseMvRxFragment() {

    private val navigationViewModel: NavigationViewModel by activityViewModel()
    private val accountViewModel: AccountViewModel by fragmentViewModel()
    private val simpleDateFormat: SimpleDateFormat by lazy { SimpleDateFormat("dd.MM.YY HH:mm", Locale.US) }
    private val logAdapter by lazy { LogAdapter() }
    private var previousState: AccountState? = null

    private lateinit var binding: FeatureAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FeatureAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logs.adapter = logAdapter
        binding.logs.disableChangeAnimations()
        binding.playgroundButton.setOnClickListener {
            findNavController().navigate(R.id.playground)
        }
    }

    override fun invalidate() = withState(accountViewModel, navigationViewModel) { accountState, navigationState ->
        if (accountState.buildData != null) {
            binding.versionNumber.asyncText = resources.getString(R.string.version_info, accountState.buildData.versionNum)
            binding.buildTime.asyncText = resources.getString(R.string.build_time_info, simpleDateFormat.format(Date(accountState.buildData.buildTime)))
            logAdapter.submitList(accountState.logs)
            if (accountState.logs != previousState?.logs) {
                binding.logs.smoothScrollToPosition(0)
            }
            if (navigationState.reselectedTab == ACCOUNT) {
                navigationViewModel.tabReselectedHandled()
                binding.logs.smoothScrollToPosition(0)
            }
        }
        previousState = accountState
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.account_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.themeSystem -> {
            accountViewModel.setTheme(Theme.SYSTEM)
            true
        }
        R.id.themeLight -> {
            accountViewModel.setTheme(Theme.LIGHT)
            true
        }
        R.id.themeDark -> {
            accountViewModel.setTheme(Theme.DARK)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun createViewModel(state: AccountState): AccountViewModel = viewModelFactory.create(state)

}
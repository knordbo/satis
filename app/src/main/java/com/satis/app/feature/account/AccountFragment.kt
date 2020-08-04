package com.satis.app.feature.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.satis.app.R
import com.satis.app.common.fragment.BaseFragment
import com.satis.app.common.logging.Logger
import com.satis.app.common.navigation.NavigationReselection
import com.satis.app.common.prefs.Theme
import com.satis.app.databinding.FeatureAccountBinding
import com.satis.app.feature.account.ui.LogAdapter
import com.satis.app.feature.account.ui.toAdapterItems
import com.satis.app.utils.view.asyncText
import com.satis.app.utils.view.disableChangeAnimations
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AccountFragment @Inject constructor(
  private val viewModelFactory: AccountViewModel.Factory,
  private val navigationReselection: NavigationReselection,
  private val logger: Logger
) : BaseFragment<FeatureAccountBinding>(), AccountViewModel.Factory by viewModelFactory {

  companion object {
    const val LOG_TAG = "AccountFragment"
  }

  private val accountViewModel: AccountViewModel by fragmentViewModel()
  private val simpleDateFormat: SimpleDateFormat by lazy { SimpleDateFormat("dd.MM.yy HH:mm", Locale.US) }
  private val logAdapter by lazy { LogAdapter() }
  private var previousState: AccountState? = null

  override val bind: (LayoutInflater, ViewGroup?, Boolean) -> FeatureAccountBinding? =
    FeatureAccountBinding::inflate

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.logs.adapter = logAdapter
    binding.logs.disableChangeAnimations()
    binding.playgroundButton.setOnClickListener {
      findNavController().navigate(R.id.playground)
    }
    binding.logText.setOnClickListener {
      logger.log(LOG_TAG, "I'm testing logging!")
    }
    navigationReselection.addReselectionListener(viewLifecycleOwner, R.id.account) {
      binding.logs.smoothScrollToPosition(0)
    }
  }

  override fun onDestroyView() {
    binding.logs.adapter = null
    super.onDestroyView()
  }

  override fun invalidate() = withState(accountViewModel) { accountState ->
    if (accountState.buildData != null) {
      binding.versionNumber.asyncText = resources.getString(R.string.version_info, accountState.buildData.versionNum)
      binding.buildTime.asyncText = resources.getString(R.string.build_time_info, simpleDateFormat.format(Date(accountState.buildData.buildTime)))
      logAdapter.submitList(accountState.logs.toAdapterItems())
      if (accountState.logs != previousState?.logs) {
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

}
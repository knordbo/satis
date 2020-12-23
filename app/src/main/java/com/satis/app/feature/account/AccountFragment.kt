package com.satis.app.feature.account

import android.os.Bundle
import android.view.*
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.fragmentViewModel
import com.satis.app.R
import com.satis.app.common.fragment.BaseFragment
import com.satis.app.common.prefs.Theme
import com.satis.app.feature.account.ui.AccountContent
import javax.inject.Inject

class AccountFragment @Inject constructor(
  private val viewModelFactory: AccountViewModel.Factory,
) : BaseFragment(), AccountViewModel.Factory by viewModelFactory {

  private val accountViewModel: AccountViewModel by fragmentViewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return ComposeView(inflater.context).apply {
      layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
      setContent {
        AccountContent(accountViewModel)
      }
    }
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
    R.id.playground -> {
      findNavController().navigate(R.id.playground)
      true
    }
    else -> super.onOptionsItemSelected(item)
  }

}

package com.satis.app.feature.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
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

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    requireActivity().addMenuProvider(
      object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
          menuInflater.inflate(R.menu.account_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
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
          else -> false
        }
      },
      viewLifecycleOwner, Lifecycle.State.RESUMED,
    )
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    return ComposeView(inflater.context).apply {
      layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT)
      setContent {
        AccountContent(accountViewModel)
      }
    }
  }

}

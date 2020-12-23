package com.satis.app.feature.account

import android.os.Bundle
import android.view.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.fragmentViewModel
import com.satis.app.R
import com.satis.app.common.fragment.BaseFragment
import com.satis.app.common.prefs.Theme
import com.satis.app.common.theme.AppTheme
import com.satis.app.feature.account.ui.DateHeaderItem
import com.satis.app.feature.account.ui.LogEntryItem
import com.satis.app.feature.account.ui.formatted
import com.satis.app.feature.account.ui.toListItems
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AccountFragment @Inject constructor(
  private val viewModelFactory: AccountViewModel.Factory,
) : BaseFragment(), AccountViewModel.Factory by viewModelFactory {

  private val accountViewModel: AccountViewModel by fragmentViewModel()
  private val simpleDateFormat: SimpleDateFormat by lazy { SimpleDateFormat("dd.MM.yy HH:mm", Locale.US) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return ComposeView(inflater.context).apply {
      layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
      setContent {
        val state = accountViewModel.stateFlow.collectAsState(AccountState())
        AppTheme {
          Column(
            modifier = Modifier.padding(Dp(16f))
          ) {
            val buildData = state.value.buildData
            if (buildData != null) {
              AccountText(
                text = getString(R.string.version_info, buildData.versionNum),
              )
              AccountText(
                text = getString(R.string.build_time_info, simpleDateFormat.format(Date(buildData.buildTime))),
              )
              AccountText(
                text = getString(R.string.log),
              )
            }
            LazyColumn {
              items(state.value.logs.toListItems()) { item ->
                when (item) {
                  is DateHeaderItem -> Text(
                    text = item.date.formatted,
                    fontSize = TextUnit.Sp(20),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = Dp(4f)),
                    color = MaterialTheme.colors.onSurface,
                  )
                  is LogEntryItem -> Text(
                    text = item.logEntry.formatted,
                    fontSize = TextUnit.Sp(12),
                    color = MaterialTheme.colors.onSurface,
                  )
                }
              }
            }
          }
        }
      }
    }
  }

  @Composable
  private fun AccountText(text: String) = Text(
    text = text,
    modifier = Modifier.padding(bottom = Dp(8f)),
    color = MaterialTheme.colors.onSurface,
    style = MaterialTheme.typography.h6,
  )

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
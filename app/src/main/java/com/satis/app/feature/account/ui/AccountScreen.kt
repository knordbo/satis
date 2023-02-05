package com.satis.app.feature.account.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.satis.app.R
import com.satis.app.common.launcher.MainActivityLauncher
import com.satis.app.common.prefs.Theme
import com.satis.app.feature.account.AccountViewModel
import java.text.SimpleDateFormat
import java.util.*

private val simpleDateFormat: SimpleDateFormat by lazy {
  SimpleDateFormat(
    "dd.MM.yy HH:mm",
    Locale.US
  )
}

@Composable
fun AccountScreen(mainActivityLauncher: MainActivityLauncher) {
  AccountScreen(hiltViewModel(), mainActivityLauncher)
}

@Composable
private fun AccountScreen(
  viewModel: AccountViewModel,
  mainActivityLauncher: MainActivityLauncher,
) {
  Scaffold(topBar = {
    AccountAppBar(viewModel)
  }) { paddingValues ->
    val state = viewModel.state.collectAsState()

    val launchAccountId = state.value.launchAccountId
    LaunchedEffect(launchAccountId) {
      if (launchAccountId != null) {
        mainActivityLauncher.launchAdjacent(launchAccountId)
        viewModel.launchAdjacentAccountHandled()
      }
    }

    val clipboardManager = LocalClipboardManager.current
    LazyColumn(
      contentPadding = PaddingValues(8.dp),
      modifier = Modifier.padding(paddingValues),
    ) {
      item {
        AccountText(
          text = stringResource(R.string.account_id, state.value.accountId),
        )
      }
      val buildData = state.value.buildData
      if (buildData != null) {
        item {
          AccountText(
            text = stringResource(R.string.version_info, buildData.versionNum),
          )
        }
        item {
          AccountText(
            text = stringResource(
              R.string.build_time_info,
              simpleDateFormat.format(Date(buildData.buildTime))
            ),
          )
        }
      }
      item {
        AccountText(
          text = stringResource(R.string.notification_token,
            state.value.notificationToken.orEmpty()),
          onClick = {
            clipboardManager.setText(
              AnnotatedString(state.value.notificationToken.orEmpty())
            )
          }
        )
      }
      item {
        AccountText(
          text = stringResource(R.string.trigger_workers),
          onClick = {
            viewModel.triggerWorkers()
          }
        )
      }
      item {
        AccountText(
          text = stringResource(R.string.log),
        )
      }
      items(state.value.logs.toListItems()) { item ->
        when (item) {
          is DateHeaderItem -> Text(
            text = item.date.formatted,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
              .padding(vertical = 4.dp)
              .fillParentMaxWidth(),
            color = MaterialTheme.colors.onSurface,
          )
          is LogEntryItem -> Text(
            text = item.logEntry.formatted,
            fontSize = 12.sp,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.fillParentMaxWidth()
          )
        }
      }
    }
  }
}

@Composable
private fun AccountAppBar(viewModel: AccountViewModel) {
  var showMenu by remember { mutableStateOf(false) }

  TopAppBar(
    title = {
      Text(stringResource(id = R.string.account))
    },
    actions = {
      IconButton(onClick = { showMenu = !showMenu }) {
        Icon(Icons.Default.MoreVert, contentDescription = null)
      }
      DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
        DropdownMenuItem(onClick = {
          viewModel.setTheme(Theme.SYSTEM)
        }) {
          Text(stringResource(id = R.string.theme_system))
        }
        DropdownMenuItem(onClick = {
          viewModel.setTheme(Theme.LIGHT)
        }) {
          Text(stringResource(id = R.string.theme_light))
        }
        DropdownMenuItem(onClick = {
          viewModel.setTheme(Theme.DARK)
        }) {
          Text(stringResource(id = R.string.theme_dark))
        }
        DropdownMenuItem(onClick = {
          viewModel.launchAdjacentAccount()
        }) {
          Text(stringResource(id = R.string.launch_adjacent_account))
        }
      }
    }
  )
}

@Composable
private fun LazyItemScope.AccountText(text: String, onClick: (() -> Unit)? = null) = Text(
  text = text,
  modifier = Modifier
    .padding(bottom = 8.dp)
    .fillParentMaxWidth()
    .then(
      if (onClick != null) {
        Modifier.clickable(onClick = onClick)
      } else {
        Modifier
      }
    ),
  color = MaterialTheme.colors.onSurface,
  style = MaterialTheme.typography.h6,
)

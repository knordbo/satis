package com.satis.app.feature.account.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satis.app.R
import com.satis.app.common.theme.AppTheme
import com.satis.app.feature.account.AccountState
import com.satis.app.feature.account.AccountViewModel
import java.text.SimpleDateFormat
import java.util.*

private val simpleDateFormat: SimpleDateFormat by lazy { SimpleDateFormat("dd.MM.yy HH:mm", Locale.US) }

@Composable
fun AccountContent(viewModel: AccountViewModel) =
  AppTheme {
    val state = viewModel.stateFlow.collectAsState(AccountState())
    val clipboardManager = AmbientClipboardManager.current
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      val buildData = state.value.buildData
      if (buildData != null) {
        AccountText(
          text = stringResource(R.string.version_info, buildData.versionNum),
        )
        AccountText(
          text = stringResource(R.string.build_time_info, simpleDateFormat.format(Date(buildData.buildTime))),
        )
      }
      AccountText(
        text = stringResource(R.string.notification_token, state.value.notificationToken.orEmpty()),
        onClick = {
          clipboardManager.setText(
            AnnotatedString(state.value.notificationToken.orEmpty())
          )
        }
      )
      AccountText(
        text = stringResource(R.string.log),
      )
      LazyColumn {
        items(state.value.logs.toListItems()) { item ->
          when (item) {
            is DateHeaderItem -> Text(
              text = item.date.formatted,
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(vertical = 4.dp),
              color = MaterialTheme.colors.onSurface,
            )
            is LogEntryItem -> Text(
              text = item.logEntry.formatted,
              fontSize = 12.sp,
              color = MaterialTheme.colors.onSurface,
            )
          }
        }
      }
    }
  }


@Composable
private fun AccountText(text: String, onClick: (() -> Unit)? = null) = Text(
  text = text,
  modifier = Modifier.padding(bottom = 8.dp).then(if (onClick != null) {
    Modifier.clickable(onClick = onClick)
  } else {
    Modifier
  }),
  color = MaterialTheme.colors.onSurface,
  style = MaterialTheme.typography.h6,
)

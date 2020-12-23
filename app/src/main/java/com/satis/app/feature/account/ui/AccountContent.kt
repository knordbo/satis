package com.satis.app.feature.account.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
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
    Column(
      modifier = Modifier.padding(Dp(16f))
    ) {
      val buildData = state.value.buildData
      if (buildData != null) {
        AccountText(
          text = stringResource(R.string.version_info, buildData.versionNum),
        )
        AccountText(
          text = stringResource(R.string.build_time_info, simpleDateFormat.format(Date(buildData.buildTime))),
        )
        AccountText(
          text = stringResource(R.string.log),
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


@Composable
private fun AccountText(text: String) = Text(
  text = text,
  modifier = Modifier.padding(bottom = Dp(8f)),
  color = MaterialTheme.colors.onSurface,
  style = MaterialTheme.typography.h6,
)

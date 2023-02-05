package com.satis.app.feature.notifications

import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.satis.app.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.minutes


@Composable
fun NotificationScreen() {
  NotificationScreen(hiltViewModel())
}

@Composable
private fun NotificationScreen(viewModel: NotificationViewModel) {
  Scaffold(topBar = {
    NotificationAppBar(viewModel)
  }) { paddingValues ->
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
      items(state.value.notifications) { notification ->
        Row(modifier = Modifier
          .padding(16.dp)
          .clickable {
            if (notification.url != null) {
              ContextCompat.startActivity(
                context,
                Intent(
                  Intent.ACTION_VIEW,
                  Uri.parse(notification.url),
                ),
                null)
            }
          }) {
          var currentTimeMillis by remember { mutableStateOf(System.currentTimeMillis()) }
          LaunchedEffect(currentTimeMillis) {
            delay(1.minutes)
            currentTimeMillis = System.currentTimeMillis()
          }
          LaunchedEffect(notification.id) {
            viewModel.notificationSeen(notification.id)
          }
          if (notification.icon != null) {
            AsyncImage(
              model = notification.icon.url,
              contentDescription = null,
              contentScale = ContentScale.Crop,
              modifier = Modifier.size(32.dp).align(Alignment.CenterVertically).let {
                if (notification.icon.useCircleCrop) it.clip(CircleShape) else it
              })
          }
          Column(
            modifier = Modifier.padding(start = 16.dp)
          ) {
            Row {
              Text(
                text = notification.title,
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
                color = MaterialTheme.colors.onBackground,
              )
              Spacer(modifier = Modifier.weight(1f))
              Text(
                text = DateUtils.getRelativeTimeSpanString(
                  notification.createdAt,
                  currentTimeMillis,
                  0
                ).toString(),
                style = TextStyle(fontSize = 14.sp),
                color = MaterialTheme.colors.onBackground,
              )
            }
            Text(
              text = notification.body,
              style = TextStyle(fontSize = 14.sp),
              color = MaterialTheme.colors.onBackground,
            )
          }
        }
      }
    }
  }
}

@Composable
fun NotificationAppBar(viewModel: NotificationViewModel) {
  TopAppBar(
    title = {
      Text(stringResource(id = R.string.notifications))
    },
    actions = {
      IconButton(onClick = { viewModel.deleteAll() }) {
        Icon(Icons.Default.Delete, contentDescription = null)
      }
    }
  )
}
package com.satis.app.feature.notifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.format.DateUtils
import android.view.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import com.satis.app.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.transform.CircleCropTransformation
import com.airbnb.mvrx.fragmentViewModel
import com.satis.app.common.fragment.BaseFragment
import com.satis.app.common.theme.AppTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import javax.inject.Inject

class NotificationFragment @Inject constructor(
  private val viewModelFactory: NotificationViewModel.Factory,
) : BaseFragment(), NotificationViewModel.Factory by viewModelFactory {

  private val notificationViewModel: NotificationViewModel by fragmentViewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return ComposeView(inflater.context).apply {
      setContent {
        val state = notificationViewModel.stateFlow.collectAsState(NotificationState())
        AppTheme {
          LazyColumn {
            items(state.value.notifications) { notification ->
              Row(modifier = Modifier
                .padding(16.dp)
                .clickable {
                  if (notification.url != null) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(notification.url)))
                  }
                }) {
                LaunchedEffect(Unit) {
                  notificationViewModel.notificationSeen(notification.id)
                }
                if (notification.icon != null) {
                  CoilImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(32.dp).align(Alignment.CenterVertically),
                    data = notification.icon.url,
                    requestBuilder = {
                      transformations(if (notification.icon.useCircleCrop) listOf(CircleCropTransformation()) else emptyList())
                    }
                  )
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
                      text = DateUtils.getRelativeTimeSpanString(notification.createdAt, System.currentTimeMillis(), 0).toString(),
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
    }
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    super.onCreateOptionsMenu(menu, inflater)
    inflater.inflate(R.menu.notification_menu, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return if (item.itemId == R.id.delete) {
      notificationViewModel.deleteAll()
      true
    } else {
      super.onOptionsItemSelected(item)
    }
  }

}

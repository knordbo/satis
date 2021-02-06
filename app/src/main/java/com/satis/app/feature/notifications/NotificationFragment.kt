package com.satis.app.feature.notifications

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return ComposeView(inflater.context).apply {
      setContent {
        val state = notificationViewModel.stateFlow.collectAsState(NotificationState())
        AppTheme {
          LazyColumn {
            items(state.value.notifications) { notification ->
              Row(modifier = Modifier.padding(16.dp)) {
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
                      style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                      text = DateUtils.getRelativeTimeSpanString(notification.createdAt, System.currentTimeMillis(), 0).toString(),
                      style = TextStyle(fontSize = 14.sp),
                    )
                  }
                  Text(
                    text = notification.body,
                    style = TextStyle(fontSize = 14.sp)
                  )
                }
              }
            }
          }
        }
      }
    }
  }

}

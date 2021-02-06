package com.satis.app.feature.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import com.airbnb.mvrx.fragmentViewModel
import com.satis.app.common.fragment.BaseFragment
import com.satis.app.common.theme.AppTheme
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
              Text(text = notification.title)
            }
          }
        }
      }
    }
  }

}

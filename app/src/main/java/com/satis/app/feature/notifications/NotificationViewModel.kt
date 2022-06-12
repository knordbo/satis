package com.satis.app.feature.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satis.app.feature.notifications.data.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class NotificationViewModel @Inject constructor(
  private val notificationRepository: NotificationRepository,
) : ViewModel(), CoroutineScope {

  override val coroutineContext: CoroutineContext
    get() = viewModelScope.coroutineContext

  private val _state: MutableStateFlow<NotificationState> =
    MutableStateFlow(value = NotificationState())
  val state: StateFlow<NotificationState> = _state.asStateFlow()


  fun load() {
    streamNotifications()
  }

  private fun streamNotifications() {
    launch {
      notificationRepository.streamNotifications().collect { notifications ->
        setState {
          copy(notifications = notifications)
        }
      }
    }
  }

  fun deleteAll() {
    launch {
      notificationRepository.deleteAll()
    }
  }

  fun notificationSeen(id: String) {
    launch {
      notificationRepository.notificationSeen(id)
    }
  }

  private fun setState(update: NotificationState.() -> NotificationState) {
    _state.value = _state.value.update()
  }
}
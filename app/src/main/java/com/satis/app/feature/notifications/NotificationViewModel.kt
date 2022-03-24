package com.satis.app.feature.notifications

import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.utils.coroutines.BaseViewModel
import com.satis.app.utils.coroutines.viewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class NotificationViewModel @AssistedInject constructor(
  @Assisted initialState: NotificationState,
  private val notificationRepository: NotificationRepository,
) : BaseViewModel<NotificationState>(
  initialState = initialState
) {
  init {
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

  interface Factory {
    fun createNotificationViewModel(initialState: NotificationState): NotificationViewModel
  }

  @AssistedFactory
  interface FactoryImpl: Factory

  companion object : MavericksViewModelFactory<NotificationViewModel, NotificationState> {
    override fun create(viewModelContext: ViewModelContext, state: NotificationState): NotificationViewModel {
      return viewModelContext.viewModelFactory<Factory>().createNotificationViewModel(state)
    }
  }
}
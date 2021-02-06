package com.satis.app.feature.notifications

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.feature.notifications.data.NotificationRepository
import com.satis.app.utils.coroutines.BaseViewModel
import com.satis.app.utils.coroutines.viewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class NotificationViewModel @AssistedInject constructor(
  @Assisted initialState: NotificationState,
  private val notificationRepository: NotificationRepository,
) : BaseViewModel<NotificationState>(
  initialState = initialState
) {
  init {
    fetchNextNotificationPage()
  }

  private fun fetchNextNotificationPage() {
    withState { currentState ->
      launch {
        val newNotifications = notificationRepository.getNotifications(
          offset = currentState.notifications.size
        )
        setState {
          copy(
            notifications = notifications + newNotifications,
            hasLoadedAllNotifications = newNotifications.isEmpty(),
          )
        }
      }
    }
  }

  @AssistedInject.Factory
  interface Factory {
    fun createNotificationViewModel(initialState: NotificationState): NotificationViewModel
  }

  companion object : MvRxViewModelFactory<NotificationViewModel, NotificationState> {
    override fun create(viewModelContext: ViewModelContext, state: NotificationState): NotificationViewModel {
      return viewModelContext.viewModelFactory<Factory>().createNotificationViewModel(state)
    }
  }
}
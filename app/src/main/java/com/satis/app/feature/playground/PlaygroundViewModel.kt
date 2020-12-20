package com.satis.app.feature.playground

import android.os.Parcelable
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.common.logging.LogEntry
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.feature.account.ui.formatted
import com.satis.app.utils.coroutines.BaseViewModel
import com.satis.app.utils.coroutines.viewModelFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class PlaygroundViewModel @AssistedInject constructor(
  @Assisted initialState: PlaygroundState,
  private val logger: PersistedLogger
) : BaseViewModel<PlaygroundState>(
  initialState = initialState
) {
  init {
    fetch("")
  }

  fun fetch(query: String) {
    launch {
      val searchResults = logger.searchLogs(query).map(LogEntry::formatted)
      setState {
        copy(items = searchResults)
      }
    }
  }

  @AssistedInject.Factory
  interface Factory {
    fun createPlaygroundViewModel(initialState: PlaygroundState): PlaygroundViewModel
  }

  companion object : MvRxViewModelFactory<PlaygroundViewModel, PlaygroundState> {
    override fun create(viewModelContext: ViewModelContext, state: PlaygroundState): PlaygroundViewModel {
      return viewModelContext.viewModelFactory<Factory>().createPlaygroundViewModel(state)
    }
  }

}

@Parcelize
data class PlaygroundState(
  val items: List<String> = emptyList()
) : MvRxState, Parcelable
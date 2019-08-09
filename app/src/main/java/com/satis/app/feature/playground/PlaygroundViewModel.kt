package com.satis.app.feature.playground

import android.os.Parcelable
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.BuildConfig
import com.satis.app.common.logging.LogEntry
import com.satis.app.common.logging.Logger
import com.satis.app.feature.account.ui.formatted
import com.satis.app.utils.coroutines.BaseViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.launch

class PlaygroundViewModel @AssistedInject constructor(
        @Assisted initialState: PlaygroundState,
        private val logger: Logger
) : BaseViewModel<PlaygroundState>(
        initialState = initialState,
        debugMode = BuildConfig.DEBUG
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
        fun create(initialState: PlaygroundState): PlaygroundViewModel
    }

    companion object : MvRxViewModelFactory<PlaygroundViewModel, PlaygroundState> {
        override fun create(viewModelContext: ViewModelContext, state: PlaygroundState): PlaygroundViewModel {
            val fragment: PlaygroundFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.createViewModel(state)
        }
    }

}

@Parcelize
data class PlaygroundState(
        val items: List<String> = emptyList()
) : MvRxState, Parcelable
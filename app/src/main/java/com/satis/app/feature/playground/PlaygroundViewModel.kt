package com.satis.app.feature.playground

import android.os.Parcelable
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.satis.app.BuildConfig
import com.satis.app.common.logging.LogEntry
import com.satis.app.common.logging.Logger
import com.satis.app.feature.account.ui.formatted
import com.satis.app.utils.coroutines.BaseViewModel
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class PlaygroundViewModel(
        initialState: PlaygroundState,
        private val logger: Logger,
        private val io: CoroutineDispatcher
) : BaseViewModel<PlaygroundState>(
        initialState = initialState,
        debugMode = BuildConfig.DEBUG
) {
    init {
        fetch("")
    }

    fun fetch(query: String) {
        launch(io) {
            val searchResults = logger.searchLogs(query).map(LogEntry::formatted)
            setState {
                copy(items = searchResults)
            }
        }
    }

    companion object : MvRxViewModelFactory<PlaygroundViewModel, PlaygroundState> {
        override fun create(viewModelContext: ViewModelContext, state: PlaygroundState): PlaygroundViewModel? =
                viewModelContext.activity.get { parametersOf(state) }
    }

}

@Parcelize
data class PlaygroundState(
        val items: List<String> = emptyList()
) : MvRxState, Parcelable
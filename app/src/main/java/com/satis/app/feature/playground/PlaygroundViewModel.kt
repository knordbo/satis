package com.satis.app.feature.playground

import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.satis.app.BuildConfig
import com.satis.app.utils.coroutines.BaseViewModel
import io.reactivex.Scheduler
import kotlinx.android.parcel.Parcelize
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class PlaygroundViewModel(
        initialState: PlaygroundState,
        private val playgroundProvider: PlaygroundProvider,
        private val io: Scheduler
) : BaseViewModel<PlaygroundState>(
        initialState = initialState,
        debugMode = BuildConfig.DEBUG
) {
    private val memoryCacheThenCall = MemoryCacheThenCall<String, List<String>> { query ->
        playgroundProvider.getItems(query)
    }

    init {
        fetch("")
    }

    fun fetch(query: String) {
        memoryCacheThenCall.call(query)
                .subscribeOn(io)
                .subscribe({ items ->
                    setState {
                        copy(items = items)
                    }
                }, {
                    // ignore
                })
                .disposeOnClear()
    }

    companion object : MvRxViewModelFactory<PlaygroundState> {
        @JvmStatic
        override fun create(activity: FragmentActivity, state: PlaygroundState): BaseMvRxViewModel<PlaygroundState> =
                activity.get<PlaygroundViewModel> { parametersOf(state) }
    }

}

@Parcelize
data class PlaygroundState(
        val items: List<String> = emptyList()
) : MvRxState, Parcelable
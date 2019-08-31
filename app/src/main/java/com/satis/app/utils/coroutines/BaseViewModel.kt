package com.satis.app.utils.coroutines

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.satis.app.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<S : MvRxState>(
        initialState: S,
        debugMode: Boolean = BuildConfig.DEBUG
) : BaseMvRxViewModel<S>(initialState, debugMode), CoroutineScope {

    init {
        logStateChanges()
    }

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

}
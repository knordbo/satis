package com.satis.app.utils.coroutines

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<S : MvRxState>(
        initialState: S,
        debugMode: Boolean = false
) : BaseMvRxViewModel<S>(initialState, debugMode), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

}
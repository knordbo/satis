package com.satis.app.utils.coroutines

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.ViewModelContext
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

inline fun <reified F> ViewModelContext.viewModelFactory(): F {
    if (this is FragmentViewModelContext) {
        var fragment: Fragment? = fragment
        while (fragment != null) {
            if (fragment is F) {
                return fragment
            }
            fragment = fragment.parentFragment
        }
    }
    return activity as? F
            ?: throw IllegalArgumentException("No view model factory found for ${F::class}")
}

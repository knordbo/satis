package com.satis.app.utils.coroutines

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.ViewModelContext
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<S : MvRxState>(
        initialState: S
) : BaseMvRxViewModel<S>(initialState), CoroutineScope {

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
            ?: throw IllegalStateException("No view model factory found for ${F::class}")
}

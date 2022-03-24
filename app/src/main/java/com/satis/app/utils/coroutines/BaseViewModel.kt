package com.satis.app.utils.coroutines

import androidx.fragment.app.Fragment
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.ViewModelContext
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<S : MavericksState>(
  initialState: S
) : MavericksViewModel<S>(initialState), CoroutineScope {

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

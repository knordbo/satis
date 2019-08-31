package com.satis.app.common.mvrx

import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.LifecycleOwner
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.MvRxViewId
import com.airbnb.mvrx.MvRxViewModelStore
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Same as [BaseMvRxFragment] except subclassing [BottomSheetDialogFragment]
 */
abstract class BaseMvRxDialogFragment : AppCompatDialogFragment(), MvRxView {

    override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }

    private val mvrxViewIdProperty = MvRxViewId()
    final override val mvrxViewId: String by mvrxViewIdProperty

    override fun onCreate(savedInstanceState: Bundle?) {
        mvrxViewModelStore.restoreViewModels(this, savedInstanceState)
        mvrxViewIdProperty.restoreFrom(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    /**
     * Fragments should override the subscriptionLifecycle owner so that subscriptions made after onCreate
     * are properly disposed as fragments are moved from/to the backstack.
     */
    override val subscriptionLifecycleOwner: LifecycleOwner
        get() = this.viewLifecycleOwnerLiveData.value ?: this

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvrxViewModelStore.saveViewModels(outState)
        mvrxViewIdProperty.saveTo(outState)
    }

    override fun onStart() {
        super.onStart()
        // This ensures that invalidate() is called for static screens that don't
        // subscribe to a ViewModel.
        postInvalidate()
    }

}
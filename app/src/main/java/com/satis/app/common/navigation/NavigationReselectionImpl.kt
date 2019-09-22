package com.satis.app.common.navigation

import androidx.annotation.IdRes
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationReselectionImpl @Inject constructor() : NavigationReselection, NavigationReselectionUpdater {

    private val callbacks = mutableMapOf<@IdRes Int, () -> Unit>()

    override fun onNavigationItemReselected(navigationId: Int) {
        callbacks[navigationId]?.invoke()
    }

    override fun addReselectionListener(lifecycleOwner: LifecycleOwner, @IdRes navigationId: Int, callback: () -> Unit) {
        callbacks[navigationId] = callback
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(ON_DESTROY)
            fun onDestroy() {
                callbacks -= navigationId
            }
        })
    }

}
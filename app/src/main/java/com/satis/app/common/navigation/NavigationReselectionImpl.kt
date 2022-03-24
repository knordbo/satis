package com.satis.app.common.navigation

import androidx.annotation.IdRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationReselectionImpl @Inject constructor() : NavigationReselection,
  NavigationReselectionUpdater {

  private val callbacks = mutableMapOf<Int, () -> Unit>()

  override fun onNavigationItemReselected(navigationId: Int) {
    callbacks[navigationId]?.invoke()
  }

  override fun addReselectionListener(
    lifecycleOwner: LifecycleOwner,
    @IdRes navigationId: Int,
    callback: () -> Unit
  ) {
    callbacks[navigationId] = callback
    lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
      override fun onDestroy(owner: LifecycleOwner) {
        callbacks -= navigationId
      }
    })
  }

}
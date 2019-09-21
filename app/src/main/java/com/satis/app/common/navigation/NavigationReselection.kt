package com.satis.app.common.navigation

import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner

interface NavigationReselection {
    @MainThread
    fun onNavigationItemReselected(@IdRes navigationId: Int)

    @MainThread
    fun addReselectionListener(lifecycleOwner: LifecycleOwner, @IdRes navigationId: Int, callback: () -> Unit)
}
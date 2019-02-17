package com.satis.app.utils.lifecycle

import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.ProcessLifecycleOwner

val isAppForeground: Boolean
    get() = ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(STARTED)

val isAppForegroundString: String
    get() = if (isAppForeground) "foreground" else "background"
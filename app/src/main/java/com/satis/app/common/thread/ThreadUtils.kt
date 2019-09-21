package com.satis.app.common.thread

import android.os.Looper

fun assertMainThread() = check(isOnMainThread()) { "Invoked off the main thread" }

fun assertBackgroundThread() = check(!isOnMainThread()) { "Invoked on the main thread" }

private fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()

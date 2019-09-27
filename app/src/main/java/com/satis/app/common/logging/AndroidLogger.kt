package com.satis.app.common.logging

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidLoggerImpl @Inject constructor(): Logger {
    override fun log(tag: String, message: String) {
        Log.d(tag, message)
    }
}
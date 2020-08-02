package com.satis.app.startup

import com.airbnb.mvrx.MvRx
import com.satis.app.BuildConfig
import javax.inject.Inject

class MvRxInitTask @Inject constructor() : StartupTask {
  override suspend fun execute() {
    MvRx.install(debugMode = BuildConfig.DEBUG)
  }
}
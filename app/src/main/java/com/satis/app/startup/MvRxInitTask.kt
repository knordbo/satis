package com.satis.app.startup

import com.airbnb.mvrx.Mavericks
import com.satis.app.BuildConfig
import javax.inject.Inject

class MvRxInitTask @Inject constructor() : StartupTask {
  override suspend fun execute() {
    Mavericks.initialize(debugMode = BuildConfig.DEBUG)
  }
}
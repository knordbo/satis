package com.satis.app.startup

import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.apply
import javax.inject.Inject

class ThemeLoaderTask @Inject constructor(
        private val prefs: Prefs
) : StartupTask {
    override fun execute() {
        prefs.theme.apply()
    }
}
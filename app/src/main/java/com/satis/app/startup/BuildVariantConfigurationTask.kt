package com.satis.app.startup

import android.content.Context
import com.satis.app.configureBuildVariantFunctionality
import javax.inject.Inject

class BuildVariantConfigurationTask @Inject constructor(
        private val context: Context
) : StartupTask {
    override fun execute() {
        configureBuildVariantFunctionality(context)
    }
}
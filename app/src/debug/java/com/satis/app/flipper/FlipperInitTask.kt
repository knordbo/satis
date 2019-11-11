package com.satis.app.flipper

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.satis.app.startup.StartupTask
import javax.inject.Inject
import javax.inject.Provider

class FlipperInitTask @Inject constructor(
        private val context: Context,
        private val flipperClient: Provider<FlipperClient>
) : StartupTask {
    override fun execute() {
        SoLoader.init(context, false)

        if (FlipperUtils.shouldEnableFlipper(context)) {
            flipperClient.get().start()
        }
    }
}
package com.satis.app.flipper

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.core.FlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.satis.app.common.annotations.Main
import com.satis.app.startup.StartupTask
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module(includes = [FlipperBindingModule::class])
object FlipperModule {

    @Provides
    @Singleton
    fun provideFlipperClient(context: Context, plugins: Set<@JvmSuppressWildcards FlipperPlugin>): FlipperClient {
        val client = AndroidFlipperClient.getInstance(context)
        plugins.forEach(client::addPlugin)
        return client
    }

    @Provides
    @IntoSet
    @Singleton
    fun provideInspectorPlugin(context: Context): FlipperPlugin =
            InspectorFlipperPlugin(context, DescriptorMapping.withDefaults())

}

@Module
abstract class FlipperBindingModule {

    @Binds
    @IntoSet
    @Main
    abstract fun provideFlipperInitTask(bind: FlipperInitTask): StartupTask

}
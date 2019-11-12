package com.satis.app.flipper

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.core.FlipperPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseDriver
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseProvider
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.satis.app.common.annotations.DatabaseName
import com.satis.app.common.annotations.Main
import com.satis.app.startup.StartupTask
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import okhttp3.Interceptor
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

    @Provides
    @IntoSet
    @Singleton
    fun provideDatabasePlugin(context: Context, @DatabaseName databaseName: String): FlipperPlugin {
        return DatabasesFlipperPlugin(SqliteDatabaseDriver(context, SqliteDatabaseProvider {
            // Some random databases like the one from firestore closes which throws flipper off,
            // therefore only listing our own database.
            context.databaseList()
                    .filter { it == databaseName }
                    .map(context::getDatabasePath)
        }))
    }

    @Provides
    @Singleton
    fun provideNetworkPlugin(): NetworkFlipperPlugin = NetworkFlipperPlugin()

    @Provides
    @IntoSet
    @Singleton
    fun provideNetworkInterceptor(plugin: NetworkFlipperPlugin): Interceptor = FlipperOkhttpInterceptor(plugin)

}

@Module
abstract class FlipperBindingModule {

    @Binds
    @IntoSet
    @Main
    abstract fun provideFlipperInitTask(bind: FlipperInitTask): StartupTask

    @Multibinds
    abstract fun provideFlipperPlugins(): Set<FlipperPlugin>

    @Binds
    @IntoSet
    @Singleton
    abstract fun provideNetworkPluginIntoSet(bind: NetworkFlipperPlugin): FlipperPlugin


}
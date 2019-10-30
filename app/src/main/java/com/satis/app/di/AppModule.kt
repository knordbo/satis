package com.satis.app.di

import android.content.Context
import androidx.fragment.app.FragmentFactory
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.satis.app.App
import com.satis.app.Database
import com.satis.app.common.annotations.Background
import com.satis.app.common.annotations.Io
import com.satis.app.common.annotations.Main
import com.satis.app.common.fragment.InjectingFragmentFactory
import com.satis.app.common.navigation.NavigationReselection
import com.satis.app.common.navigation.NavigationReselectionImpl
import com.satis.app.common.navigation.NavigationReselectionUpdater
import com.satis.app.common.prefs.Prefs
import com.satis.app.common.prefs.PrefsImpl
import com.satis.app.utils.network.clientProvider
import com.satis.app.utils.network.jsonMediaType
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module(includes = [AppBindingModule::class])
object AppModule {

    @Provides
    fun provideContext(application: App): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideJson(): Json = Json.nonstrict

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: Provider<OkHttpClient>, json: Json): Retrofit = Retrofit.Builder()
            .baseUrl("https://dummy.com/")
            .clientProvider(okHttpClient)
            .addConverterFactory(json.asConverterFactory(jsonMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database = Database(AndroidSqliteDriver(Database.Schema, context, "satis.sqldelight.db"))

    @Provides
    @Singleton
    @Main
    fun provideMainCoroutineContext(): CoroutineContext = Dispatchers.Main.immediate

    @Provides
    @Singleton
    @Background
    fun provideBackgroundCoroutineContext(): CoroutineContext = Dispatchers.Default

    @Provides
    @Singleton
    @Io
    fun provideIoCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    fun provideAppUpdateManager(context: Context): AppUpdateManager = AppUpdateManagerFactory.create(context)

    @Provides
    @Singleton
    fun provideUserId(prefs: Prefs) = prefs.userId

}

@Module
abstract class AppBindingModule {

    @Binds
    abstract fun providePrefs(bind: PrefsImpl): Prefs

    @Binds
    abstract fun provideFragmentFactory(bind: InjectingFragmentFactory): FragmentFactory

    @Binds
    abstract fun provideNavigationReselection(bind: NavigationReselectionImpl): NavigationReselection

    @Binds
    abstract fun provideNavigationReselectionUpdater(bind: NavigationReselectionImpl): NavigationReselectionUpdater

}
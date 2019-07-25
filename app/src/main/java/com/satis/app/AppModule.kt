package com.satis.app

import android.content.Context
import androidx.fragment.app.FragmentFactory
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.satis.app.common.AppDatabase
import com.satis.app.common.fragment.InjectingFragmentFactory
import com.satis.app.common.keyvalue.DefaultKeyValueProvider
import com.satis.app.common.keyvalue.KeyValueDao
import com.satis.app.common.keyvalue.KeyValueProvider
import com.satis.app.common.logging.LogDao
import com.satis.app.common.logging.Logger
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.common.prefs.DefaultPrefs
import com.satis.app.common.prefs.Prefs
import com.satis.app.utils.retrofit.jsonMediaType
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [AppBindingModule::class])
class AppModule {
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
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit = Retrofit.Builder()
            .baseUrl("https://dummy.com/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(jsonMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase = AppDatabase.createDatabase(context)

    @Provides
    @Singleton
    fun provideKeyValueDao(appDatabase: AppDatabase): KeyValueDao = appDatabase.keyValueDao()

    @Provides
    @Singleton
    fun provideLogDao(appDatabase: AppDatabase): LogDao = appDatabase.logDao()

    @Provides
    @Singleton
    @Main
    fun provideMainCoroutineDispatcher() = Dispatchers.Main

    @Provides
    @Singleton
    @Io
    fun provideIoCoroutineDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideAppUpdateManager(context: Context) = AppUpdateManagerFactory.create(context)

}

@Module
abstract class AppBindingModule {

    @Binds
    @Singleton
    abstract fun provideKeyValueProvider(bind: DefaultKeyValueProvider): KeyValueProvider

    @Binds
    @Singleton
    abstract fun provideLogger(bind: PersistedLogger): Logger

    @Binds
    @Singleton
    abstract fun providePrefs(bind: DefaultPrefs): Prefs

    @Binds
    abstract fun provideFragmentFactory(bind: InjectingFragmentFactory): FragmentFactory

}
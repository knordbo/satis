package com.satis.app

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.satis.app.common.AppDatabase
import com.satis.app.common.keyvalue.DefaultKeyValueProvider
import com.satis.app.common.keyvalue.KeyValueDao
import com.satis.app.common.keyvalue.KeyValueProvider
import com.satis.app.common.logging.LogDao
import com.satis.app.common.logging.Logger
import com.satis.app.common.logging.PersistedLogger
import com.satis.app.common.prefs.DefaultPrefs
import com.satis.app.common.prefs.Prefs
import com.satis.app.utils.retrofit.jsonMediaType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<Json> { Json.nonstrict }
    single<OkHttpClient> { OkHttpClient() }
    single<Retrofit> {
        Retrofit.Builder()
                .baseUrl("https://dummy.com/")
                .client(get())
                .addConverterFactory(get<Json>().asConverterFactory(jsonMediaType()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    single<FragmentFactory> {
        object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return try {
                    get(clazz = loadFragmentClass(classLoader, className).kotlin, qualifier = null, parameters = null)
                } catch (t: Throwable) {
                    super.instantiate(classLoader, className)
                }
            }
        }
    }

    single<AppDatabase> { AppDatabase.createDatabase(get()) }
    single<KeyValueDao> { get<AppDatabase>().keyValueDao() }
    single<KeyValueProvider> { DefaultKeyValueProvider(get(), get(), get(named<Io>())) }
    single<LogDao> { get<AppDatabase>().logDao() }
    single<Logger> { PersistedLogger(get(), get(named<Io>())) }
    single<Prefs> { DefaultPrefs(get()) }

    single<CoroutineDispatcher>(named<Main>()) { Dispatchers.Main }
    single<CoroutineDispatcher>(named<Io>()) { Dispatchers.IO }

    single<AppUpdateManager> { AppUpdateManagerFactory.create(get()) }
    factory<ImmediateAppUpdater> { (activity: Activity) -> ImmediateAppUpdater(activity, get()) }
}

annotation class Io
annotation class Main
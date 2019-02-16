package com.satis.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.serializationConverterFactory
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
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import org.koin.experimental.builder.getForClass
import retrofit2.Retrofit

val appModule = module {
    single<Json> { Json.nonstrict }
    single<OkHttpClient> { OkHttpClient() }
    single<Retrofit> {
        Retrofit.Builder()
                .baseUrl("https://dummy.com/")
                .client(get())
                .addConverterFactory(serializationConverterFactory(jsonMediaType(), get<Json>()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    single<FragmentFactory> {
        object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String, args: Bundle?): Fragment {
                return try {
                    getForClass(clazz = loadFragmentClass(classLoader, className))
                } catch (t: Throwable) {
                    super.instantiate(classLoader, className, args)
                }
            }
        }
    }

    single<AppDatabase> { AppDatabase.createDatabase(get()) }
    single<KeyValueDao> { get<AppDatabase>().keyValueDao() }
    single<KeyValueProvider> { DefaultKeyValueProvider(get(), get(), get(IO)) }
    single<LogDao> { get<AppDatabase>().logDao() }
    single<Logger> { PersistedLogger(get(), get(IO)) }
    single<Prefs> { DefaultPrefs(get()) }

    single<Scheduler>(MAIN) { mainThread() }
    single<Scheduler>(IO) { io() }

    single<CoroutineDispatcher>(MAIN) { Dispatchers.Main }
    single<CoroutineDispatcher>(IO) { Dispatchers.IO }
}

const val IO = "io"
const val MAIN = "main"
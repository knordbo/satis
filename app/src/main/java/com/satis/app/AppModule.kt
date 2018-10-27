package com.satis.app

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.stringBased
import com.satis.app.common.AppDatabase
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
import kotlinx.serialization.json.JSON
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit

val appModule = module {
    single<JSON> { JSON.nonstrict }
    single<OkHttpClient> { OkHttpClient() }
    single<Retrofit> {
        Retrofit.Builder()
                .baseUrl("https://dummy.com/")
                .client(get())
                .addConverterFactory(stringBased(jsonMediaType(), get<JSON>()::parse, get<JSON>()::stringify))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    single<AppDatabase> { AppDatabase.createDatabase(get()) }
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
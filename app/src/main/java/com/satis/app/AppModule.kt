package com.satis.app

import com.satis.app.common.AppDatabase
import com.satis.app.common.Prefs
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module.module
import kotlin.coroutines.CoroutineContext

val appModule = module {
    single<AppDatabase> {
        AppDatabase.createDatabase(get())
    }

    single<Prefs> {
        Prefs(get())
    }

    single<Scheduler>(IO) {
        io()
    }

    single<Scheduler>(MAIN) {
        mainThread()
    }

    single<CoroutineContext>(IO) {
        Dispatchers.IO
    }

    single<CoroutineContext>(MAIN) {
        Dispatchers.Main
    }
}

const val IO = "io"
const val MAIN = "main"
package com.satis.app

import com.satis.app.common.AppDatabase
import com.satis.app.common.Prefs
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import org.koin.dsl.module.module

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
}

const val IO = "io"
const val MAIN = "main"
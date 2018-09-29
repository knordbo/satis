package com.satis.app

import com.satis.app.common.AppDatabase
import com.satis.app.common.Prefs
import org.koin.dsl.module.module

val appModule = module {
    single<AppDatabase> {
        AppDatabase.createDatabase(get())
    }

    single<Prefs> {
        Prefs(get())
    }
}
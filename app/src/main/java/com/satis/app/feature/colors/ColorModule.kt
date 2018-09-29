package com.satis.app.feature.colors

import com.satis.app.common.AppDatabase
import com.satis.app.feature.colors.persistence.ColorDao
import org.koin.dsl.module.module

val colorModule = module {
    single<ColorDao> {
        get<AppDatabase>().colorDao()
    }
}
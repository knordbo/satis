package com.satis.app

import android.content.Context
import com.satis.app.common.AppDatabase
import com.satis.app.feature.colors.persistence.ColorDao
import com.satis.app.feature.colors.ui.ColorViewModelFactory
import dagger.Module
import dagger.Provides

// TODO modules per feature instead
@Module
class AppModule(private val context: Context) {
    @Provides
    fun provideAppDatabase(): AppDatabase = AppDatabase.createDatabase(context)

    @Provides
    fun provideColorDao(appDatabase: AppDatabase): ColorDao = appDatabase.colorDao()

    @Provides
    fun provideViewModelFactory(colorDao: ColorDao): ColorViewModelFactory = ColorViewModelFactory(colorDao)
}
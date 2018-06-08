package com.satis.app

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.satis.app.common.AppDatabase
import com.satis.app.common.Prefs
import com.satis.app.feature.cards.data.CardProvider
import com.satis.app.feature.cards.data.DefaultCardProvider
import com.satis.app.feature.colors.persistence.ColorDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// TODO modules per feature instead
@Module
class AppModule(private val context: Context) {
    @Provides @Singleton
    fun provideAppDatabase(): AppDatabase = AppDatabase.createDatabase(context)

    @Provides @Singleton
    fun provideColorDao(appDatabase: AppDatabase): ColorDao = appDatabase.colorDao()

    @Provides @Singleton
    fun providePrefs() = Prefs(context)

    @Provides @Singleton
    fun provideCardProvider(prefs: Prefs): CardProvider = DefaultCardProvider(prefs.getUniqueId(), FirebaseFirestore.getInstance())
}
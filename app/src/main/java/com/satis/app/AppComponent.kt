package com.satis.app

import com.satis.app.common.Prefs
import com.satis.app.feature.cards.data.CardProvider
import com.satis.app.feature.colors.persistence.ColorDao
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun colorDao(): ColorDao
    fun cardProvider(): CardProvider
    fun prefs(): Prefs
}
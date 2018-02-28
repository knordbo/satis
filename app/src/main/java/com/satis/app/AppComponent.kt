package com.satis.app

import com.satis.app.feature.colors.ui.ColorViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun colorViewModelFactory(): ColorViewModelFactory
}
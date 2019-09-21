package com.satis.app.di

import com.satis.app.App
import com.satis.app.feature.account.AccountModule
import com.satis.app.feature.cards.CardModule
import com.satis.app.feature.images.ImagesModule
import com.satis.app.feature.playground.PlaygroundModule
import com.satis.app.startup.StartupModule
import com.satis.app.work.WorkerModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AssistedModule::class,
    AppModule::class,
    StartupModule::class,
    MainActivityModule::class,
    WorkerModule::class,
    AccountModule::class,
    CardModule::class,
    PlaygroundModule::class,
    ImagesModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: App): AppComponent
    }

}
package com.satis.app.di

import com.satis.app.App
import com.satis.app.MainActivity
import com.satis.app.common.logging.LogModule
import com.satis.app.feature.account.AccountModule
import com.satis.app.feature.cards.CardModule
import com.satis.app.feature.images.ImagesModule
import com.satis.app.feature.playground.PlaygroundModule
import com.satis.app.startup.StartupModule
import com.satis.app.work.WorkerModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AssistedModule::class,
    AppModule::class,
    LogModule::class,
    StartupModule::class,
    WorkerModule::class,
    AccountModule::class,
    CardModule::class,
    PlaygroundModule::class,
    ImagesModule::class,
    VariantModule::class
])
interface AppComponent {

    fun inject(app: App)
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: App): AppComponent
    }

}
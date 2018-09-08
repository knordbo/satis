package com.satis.app

import android.app.Application
import android.content.Context
import com.satis.app.work.WorkScheduler

class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

//        configureVariant()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        appComponent.prefs().log("App", "App startup")
        WorkScheduler().schedule(appComponent.prefs())
    }

    fun appComponent() = appComponent

}

fun Context.appComponent(): AppComponent = (applicationContext as App).appComponent()

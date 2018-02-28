package com.satis.app

import android.app.Application
import android.content.Context
import com.codemonkeylabs.fpslibrary.TinyDancer

class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        TinyDancer.create().show(this)

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun appComponent() = appComponent

}

fun Context.appComponent(): AppComponent = (applicationContext as App).appComponent()

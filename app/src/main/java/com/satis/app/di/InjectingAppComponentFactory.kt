package com.satis.app.di

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Intent
import androidx.core.app.AppComponentFactory

class InjectingAppComponentFactory : AppComponentFactory() {

  private val appComponent = DaggerAppComponent.create()

  override fun instantiateApplicationCompat(cl: ClassLoader, className: String): Application =
    appComponent.provideApp()

  override fun instantiateActivityCompat(cl: ClassLoader, className: String, intent: Intent?): Activity =
    appComponent.provideActivityFactory().instantiateActivityCompat(cl, className, intent)

  override fun instantiateServiceCompat(cl: ClassLoader, className: String, intent: Intent?): Service =
    appComponent.provideServiceFactory().instantiateServiceCompat(cl, className, intent)
}

package com.satis.app

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.core.app.AppComponentFactory
import com.satis.app.di.DaggerAppComponent

class InjectingAppComponentFactory : AppComponentFactory() {

  private val appComponent = DaggerAppComponent.create()

  override fun instantiateApplicationCompat(cl: ClassLoader, className: String): Application =
      appComponent.provideApp()

  override fun instantiateActivityCompat(cl: ClassLoader, className: String, intent: Intent?): Activity =
      appComponent.provideMainActivity()
}
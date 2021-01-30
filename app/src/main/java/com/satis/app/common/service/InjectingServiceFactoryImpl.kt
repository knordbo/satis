package com.satis.app.common.service

import android.app.Service
import android.content.Intent
import androidx.core.app.AppComponentFactory
import javax.inject.Inject
import javax.inject.Provider

class InjectingServiceFactoryImpl @Inject constructor(
  private val creators: Map<Class<out Service>, @JvmSuppressWildcards Provider<Service>>
) : AppComponentFactory(), InjectingServiceFactory {

  override fun instantiateServiceCompat(cl: ClassLoader, className: String, intent: Intent?): Service {
    val creator = creators[Class.forName(className, false, cl)]

    return if (creator != null) {
      creator.get()
    } else {
      super.instantiateServiceCompat(cl, className, intent)
    }
  }

}

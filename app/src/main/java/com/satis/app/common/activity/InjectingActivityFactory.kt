package com.satis.app.common.activity

import android.app.Activity
import android.content.Intent
import androidx.core.app.AppComponentFactory
import javax.inject.Inject
import javax.inject.Provider

class InjectingActivityFactory @Inject constructor(
    private val creators: Map<Class<out Activity>, @JvmSuppressWildcards Provider<Activity>>
) : AppComponentFactory() {

  override fun instantiateActivityCompat(cl: ClassLoader, className: String, intent: Intent?): Activity {
    val creator = creators[Class.forName(className, false, cl)]

    return if (creator != null) {
      creator.get()
    } else {
      super.instantiateActivityCompat(cl, className, intent)
    }
  }

}

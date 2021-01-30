package com.satis.app.common.service

import android.app.Service
import android.content.Intent

interface InjectingServiceFactory {
  fun instantiateServiceCompat(cl: ClassLoader, className: String, intent: Intent?): Service
}

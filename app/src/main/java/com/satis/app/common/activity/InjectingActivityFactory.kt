package com.satis.app.common.activity

import android.app.Activity
import android.content.Intent

interface InjectingActivityFactory {
  fun instantiateActivityCompat(cl: ClassLoader, className: String, intent: Intent?): Activity
}
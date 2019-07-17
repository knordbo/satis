package com.satis.app

import android.content.Context
import com.facebook.stetho.Stetho

fun configureBuildVariantFunctionality(context: Context) {
    Stetho.initializeWithDefaults(context)
}
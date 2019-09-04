package com.satis.app.utils.context

import android.content.Context
import androidx.annotation.DrawableRes

fun Context.requireDrawable(@DrawableRes id: Int) = requireNotNull(getDrawable(id)) {
    "no drawable found for id: $id"
}
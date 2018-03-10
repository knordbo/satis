package com.satis.app

import android.app.Application
import com.codemonkeylabs.fpslibrary.TinyDancer

fun Application.configureVariant() {
    TinyDancer.create()
            .startingYPosition(TINY_DANCER_OFFSET)
            .startingXPosition(TINY_DANCER_OFFSET)
            .show(this)
}

private const val TINY_DANCER_OFFSET = 20
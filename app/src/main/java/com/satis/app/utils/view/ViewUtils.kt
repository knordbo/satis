package com.satis.app.utils.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

val View.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)

fun ViewGroup.inflate(@LayoutRes resource: Int, attach: Boolean = true) {
    layoutInflater.inflate(resource, this, attach)
}

fun RecyclerView.disableChangeAnimations() {
    (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
}
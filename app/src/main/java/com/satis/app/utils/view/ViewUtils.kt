package com.satis.app.utils.view

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

val View.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)

fun ViewGroup.inflate(@LayoutRes resource: Int, attach: Boolean = true) {
    layoutInflater.inflate(resource, this, attach)
}

fun RecyclerView.disableChangeAnimations() {
    (this.itemAnimator as? SimpleItemAnimator)!!.supportsChangeAnimations = false
}
package com.satis.app.utils.view

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

val View.layoutInflater: LayoutInflater
  get() = LayoutInflater.from(context)

fun RecyclerView.disableChangeAnimations() {
  (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
}
package com.satis.app.conductor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseController : ViewModelController() {

    abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    open fun onViewInflated(view: View) {}

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflateView(inflater, container).apply {
                onViewInflated(this)
            }

}
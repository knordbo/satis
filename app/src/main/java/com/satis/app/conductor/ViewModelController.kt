package com.satis.app.conductor

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProvider.AndroidViewModelFactory
import android.arch.lifecycle.ViewModelProvider.NewInstanceFactory
import android.arch.lifecycle.ViewModelStore
import android.os.Bundle
import com.bluelinelabs.conductor.Controller

abstract class ViewModelController(bundle: Bundle? = null) : Controller(bundle), LifecycleOwner {

    private val viewModelStore = ViewModelStore()
    private val lifecycleOwner = ControllerLifecycleOwner(this)

    protected fun viewModelProvider(factory: NewInstanceFactory = AndroidViewModelFactory.getInstance(activity!!.application)) =
            ViewModelProvider(viewModelStore, factory)

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }

    override fun getLifecycle() = lifecycleOwner.lifecycle
}
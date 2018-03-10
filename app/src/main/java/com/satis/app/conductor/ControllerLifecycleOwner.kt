package com.satis.app.conductor

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.view.View
import com.bluelinelabs.conductor.Controller


class ControllerLifecycleOwner(controller: Controller) : Controller.LifecycleListener(), LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        controller.addLifecycleListener(object : Controller.LifecycleListener() {
            override fun preCreateView(controller: Controller) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            }

            override fun postAttach(controller: Controller, view: View) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            }

            override fun preDetach(controller: Controller, view: View) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            }

            override fun postDestroyView(controller: Controller) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            }
        })
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}
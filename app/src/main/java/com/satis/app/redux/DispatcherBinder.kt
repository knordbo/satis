package com.satis.app.redux

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event.ON_START
import android.arch.lifecycle.Lifecycle.Event.ON_STOP
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.satis.app.utils.rx.safeDispose
import io.reactivex.disposables.Disposable

class DispatcherBinder<State, Action, ViewState>(
        lifecycle: Lifecycle,
        private val dispatcher: ReduxDipatcher<State, Action, ViewState>,
        private val onNext: (ViewState) -> Unit) : LifecycleObserver {

    private var disposable: Disposable? = null

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(ON_START)
    fun onStart() {
        disposable = dispatcher.subscribe(onNext)
    }

    @OnLifecycleEvent(ON_STOP)
    fun onStop() {
        disposable?.safeDispose()
    }
}

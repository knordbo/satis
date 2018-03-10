package com.satis.app.redux

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable

class DispatcherBinder<S : State, VS : ViewState>(
        lifecycle: Lifecycle,
        private val reduxDipatcher: ReduxDipatcher<S, VS>,
        private val onNext: (VS) -> Unit) : LifecycleObserver {

    private var disposable: Disposable? = null

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        disposable = reduxDipatcher.subscribe(onNext)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }
}

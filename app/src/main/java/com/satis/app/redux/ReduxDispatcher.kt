package com.satis.app.redux

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ReduxDipatcher<S : State, out VS : ViewState>(protected val store: Store<S>,
                                                             private val mainScheduler: Scheduler,
                                                             private val viewStateMapper: (S) -> VS) {

    protected open val state: Flowable<S> = store.asObservable()
    private val disposables = CompositeDisposable()

    fun subscribe(onNext: (viewState: VS) -> Unit): Disposable = state
            .map(viewStateMapper)
            .observeOn(mainScheduler)
            .subscribe(onNext)

    fun onCleared() {
        store.tearDown()
        disposables.clear()
    }
}



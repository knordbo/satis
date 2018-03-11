package com.satis.app.redux

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ReduxDipatcher<State, in Action, out ViewState>(
        protected val store: Store<State, Action>,
        private val mainScheduler: Scheduler,
        private val viewStateMapper: (State) -> ViewState) {

    private val state: Flowable<State> = store.asFlowable()
    private val disposables = CompositeDisposable()

    fun subscribe(onNext: (viewState: ViewState) -> Unit): Disposable = state
            .map(viewStateMapper)
            .observeOn(mainScheduler)
            .subscribe(onNext)

    fun onCleared() {
        store.tearDown()
        disposables.clear()
    }
}



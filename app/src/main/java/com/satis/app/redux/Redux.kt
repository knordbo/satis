package com.satis.app.redux

import com.satis.app.utils.rx.plusAssign
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

interface Reducer<State, in Action> {
    fun reduce(oldState: State, action: Action): State
}

interface Store<State, in Action> {
    fun dispatch(action: Action)
    fun dispatch(actions: Flowable<out Action>)
    fun asFlowable(): Flowable<State>
    fun tearDown()
}

class DefaultStore<State, in Action>(initialValue: State, reducer: Reducer<State, Action>) : Store<State, Action> {

    private val actionsSubject = PublishSubject.create<Action>()
    private val statesSubject = BehaviorSubject.create<State>()
    private val disposables = CompositeDisposable()

    init {
        disposables += actionsSubject
                .scan(initialValue, reducer::reduce)
                .distinctUntilChanged()
                .subscribe { statesSubject.onNext(it) }
    }

    override fun dispatch(action: Action) {
        actionsSubject.onNext(action)
    }

    override fun dispatch(actions: Flowable<out Action>) {
        disposables += actions.subscribe { dispatch(it) }
    }

    override fun asFlowable(): Flowable<State> = statesSubject.toFlowable(BackpressureStrategy.LATEST)

    override fun tearDown() {
        disposables.clear()
    }
}
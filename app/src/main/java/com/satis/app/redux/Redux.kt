package com.satis.app.redux

import com.satis.app.utils.rx.plusAssign
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

interface Action // TODO get rid off and change S to State generic and Action generic etc

interface State // TODO get rid off and change S to State generic and Action generic etc

interface Reducer<S : State> {
    fun reduce(oldState: S, action: Action): S
}

interface Store<S : State> {
    fun dispatch(action: Action)
    fun dispatch(actions: Flowable<out Action>)
    fun asObservable(): Flowable<S>
    fun currentState(): S
    fun tearDown()
}

class SimpleStore<S : State>(private val initialValue: S, reducer: Reducer<S>) : Store<S> {

    private val actionsSubject = PublishSubject.create<Action>()
    private val statesSubject = BehaviorSubject.create<S>()

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

    override fun asObservable(): Flowable<S> = statesSubject.toFlowable(BackpressureStrategy.LATEST)

    override fun currentState(): S = if (statesSubject.hasValue()) statesSubject.value else initialValue

    override fun tearDown() {
        disposables.clear()
    }
}
package com.satis.app.utils.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.safeDispose() = if (!isDisposed) dispose() else Unit

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    this.add(disposable)
}
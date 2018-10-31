package com.satis.app.utils.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

inline fun <X, Y> LiveData<X>.map(crossinline transform: (X) -> Y): LiveData<Y> = Transformations.map(this) {
    transform(it)
}
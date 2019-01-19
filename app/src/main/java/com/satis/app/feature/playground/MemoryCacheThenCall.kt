package com.satis.app.feature.playground

import io.reactivex.Observable
import io.reactivex.Single

class SimpleLruCache<K : Any, V : Any>(private val capacity: Int = 100) : LinkedHashMap<K, V>() {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean = size > capacity
}

class MemoryCacheThenCall<K : Any, V : Any>(private val source: (K) -> Single<V>) {

    private val cache = SimpleLruCache<K, V>()

    fun call(key: K): Observable<V> {
        val existingValue: V? = synchronized(this) { cache[key] }
        val call = source(key)
                .flatMapObservable {
                    synchronized(this) { cache[key] = it }
                    Observable.just(it)
                }
        return if (existingValue != null) {
            Observable.concat(Observable.just(existingValue), call)
        } else {
            call
        }
    }

}
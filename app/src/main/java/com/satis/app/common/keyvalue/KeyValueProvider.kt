package com.satis.app.common.keyvalue

import kotlinx.coroutines.channels.ReceiveChannel

interface KeyValueProvider {
    suspend fun <T : Any> get(key: Key<T>): T?
    fun <T : Any> getStream(key: Key<T>): ReceiveChannel<T>
    suspend fun <T : Any> insert(key: Key<T>, value: T)
}

data class Key<T : Any>(val id: String, val type: Class<T>) {
    companion object {
        inline fun <reified T : Any> of(id: String) = Key(id, T::class.java)
    }
}
package com.satis.app.common.keyvalue

import kotlinx.coroutines.flow.Flow

interface KeyValueRepository {
    suspend fun <T : Any> insert(key: Key<T>, value: T)
    suspend fun <T : Any> get(key: Key<T>): T?
    fun <T : Any> getStream(key: Key<T>): Flow<T>
}

data class Key<T : Any>(val id: String, val type: Class<T>) {
    companion object {
        inline fun <reified T : Any> of(id: String) = Key(id, T::class.java)
    }
}
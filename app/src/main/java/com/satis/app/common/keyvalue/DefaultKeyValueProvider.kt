package com.satis.app.common.keyvalue

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.flow.asFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializerByTypeToken

class DefaultKeyValueProvider(
        private val keyValueDao: KeyValueDao,
        private val json: Json,
        private val io: CoroutineDispatcher
) : KeyValueProvider {

    override suspend fun <T : Any> insert(key: Key<T>, value: T) {
        withContext(io) {
            keyValueDao.insert(KeyValueEntity(key.id, stringify(key, value)))
        }
    }

    override suspend fun <T : Any> get(key: Key<T>): T? = withContext(io) {
        keyValueDao.get(key.id)?.let { keyValue ->
            parse(key, keyValue)
        }
    }

    override fun <T : Any> getStream(key: Key<T>): Flow<T> = keyValueDao.getStream(key.id).map { keyValue ->
        parse(key, keyValue)
    }.asFlow()

    private fun <T : Any> stringify(key: Key<T>, value: T): String = json.stringify(serializerByTypeToken(key.type), value)

    private fun <T : Any> parse(key: Key<T>, keyValue: KeyValueEntity): T =
            json.parse(serializerByTypeToken(key.type), keyValue.value) as T
}
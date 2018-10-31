package com.satis.app.common.keyvalue

import io.reactivex.Flowable
import kotlinx.serialization.json.JSON
import kotlinx.serialization.serializerByTypeToken

class DefaultKeyValueProvider(
        private val keyValueDao: KeyValueDao,
        private val json: JSON
) : KeyValueProvider {

    override fun <T : Any> get(key: Key<T>): T? = keyValueDao.get(key.id)?.let { keyValue ->
        parse(key, keyValue)
    }

    override fun <T : Any> getStream(key: Key<T>): Flowable<T> = keyValueDao.getStream(key.id).map { keyValue ->
        parse(key, keyValue)
    }

    override fun <T : Any> insert(key: Key<T>, value: T) {
        keyValueDao.insert(KeyValueEntity(key.id, stringify(key, value)))
    }

    private fun <T : Any> stringify(key: Key<T>, value: T): String = json.stringify(serializerByTypeToken(key.type), value)

    private fun <T : Any> parse(key: Key<T>, keyValue: KeyValueEntity): T =
            json.parse(serializerByTypeToken(key.type), keyValue.value) as T

}
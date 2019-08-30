package com.satis.app.common.keyvalue

import com.satis.app.common.annotations.Io
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializerByTypeToken
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class KeyValueRepositoryImpl @Inject constructor(
        private val keyValueDao: KeyValueDao,
        private val json: Json,
        @Io private val io: CoroutineContext
) : KeyValueRepository {

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

    override fun <T : Any> getStream(key: Key<T>): Flow<T> = keyValueDao.getStream(key.id)
            .filterNotNull()
            .map { keyValue ->
                parse(key, keyValue)
            }
            .flowOn(io)

    private fun <T : Any> stringify(key: Key<T>, value: T): String = json.stringify(serializerByTypeToken(key.type), value)

    private fun <T : Any> parse(key: Key<T>, keyValue: KeyValueEntity): T =
            json.parse(serializerByTypeToken(key.type), keyValue.value) as T
}
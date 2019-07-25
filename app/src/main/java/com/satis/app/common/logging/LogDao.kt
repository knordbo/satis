package com.satis.app.common.logging

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface LogDao {
    @Insert
    suspend fun insertLog(logEntity: LogEntity)

    @Query("SELECT * FROM log WHERE tag like '%' || :tagQuery || '%' OR message like '%' || :messageQuery|| '%' ORDER BY timestamp DESC LIMIT 1000")
    suspend fun searchLogs(tagQuery: String, messageQuery: String): List<LogEntity>

    // TODO use flow when supported
    @Query("SELECT * FROM log ORDER BY timestamp DESC LIMIT 1000")
    fun getLogStream(): Flowable<List<LogEntity>>
}
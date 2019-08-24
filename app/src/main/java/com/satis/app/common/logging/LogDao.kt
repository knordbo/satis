package com.satis.app.common.logging

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {
    @Insert
    suspend fun insertLog(logEntity: LogEntity)

    @Query("SELECT * FROM log WHERE tag like '%' || :tagQuery || '%' OR message like '%' || :messageQuery|| '%' ORDER BY timestamp DESC LIMIT 1000")
    suspend fun searchLogs(tagQuery: String, messageQuery: String): List<LogEntity>

    @Query("SELECT * FROM log ORDER BY timestamp DESC LIMIT 1000")
    fun getLogStream(): Flow<List<LogEntity>>
}
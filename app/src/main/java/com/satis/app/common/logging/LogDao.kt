package com.satis.app.common.logging

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface LogDao {
    @Insert
    suspend fun insertLog(logEntity: LogEntity)

    // TODO use channel when supported
    @Query("SELECT * FROM log ORDER BY timestamp DESC LIMIT 1000")
    fun getLogStream(): Flowable<List<LogEntity>>
}
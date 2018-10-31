package com.satis.app.common.logging

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface LogDao {
    @Insert
    fun insertLog(logEntity: LogEntity)

    @Query("SELECT * FROM log")
    fun getLogStream(): Flowable<List<LogEntity>>
}
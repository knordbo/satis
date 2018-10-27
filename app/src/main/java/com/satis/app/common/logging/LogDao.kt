package com.satis.app.common.logging

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LogDao {
    @Insert
    fun insertLog(logEntity: LogEntity)

    @Query("SELECT * FROM log")
    fun getLogStream(): LiveData<List<LogEntity>>
}
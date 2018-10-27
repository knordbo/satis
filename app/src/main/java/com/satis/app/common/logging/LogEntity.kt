package com.satis.app.common.logging

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log")
data class LogEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val timestamp: Long,
        val tag: String,
        val message: String
)
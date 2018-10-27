package com.satis.app.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.satis.app.common.logging.LogDao
import com.satis.app.common.logging.LogEntity

@Database(
        entities = [LogEntity::class],
        version = 2,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun logDao(): LogDao

    companion object {
        fun createDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "satis.db")
                        .build()
    }
}
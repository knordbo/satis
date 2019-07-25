package com.satis.app.common.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.satis.app.common.keyvalue.KeyValueDao
import com.satis.app.common.keyvalue.KeyValueEntity
import com.satis.app.common.logging.LogDao
import com.satis.app.common.logging.LogEntity

@Database(
        entities = [LogEntity::class, KeyValueEntity::class],
        version = 3,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun logDao(): LogDao

    abstract fun keyValueDao(): KeyValueDao

    companion object {
        fun createDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "satis.db")
                        .fallbackToDestructiveMigration()
                        .build()
    }
}
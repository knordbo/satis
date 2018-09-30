package com.satis.app.common

import android.arch.persistence.room.Database
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(
        entities = [DummyEntity::class],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun createDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "satis.db")
                        .build()
    }
}

@Entity(tableName = "dummy")
private data class DummyEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0, val value: String)
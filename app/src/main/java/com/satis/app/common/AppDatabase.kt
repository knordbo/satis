package com.satis.app.common

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase

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
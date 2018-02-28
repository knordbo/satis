package com.satis.app.common

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.satis.app.feature.colors.persistence.ColorDao
import com.satis.app.feature.colors.persistence.ColorEntity

@Database(entities = [(ColorEntity::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun colorDao(): ColorDao

    companion object {
        fun createDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "satis.db")
                        .build()
    }
}
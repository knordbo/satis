package com.satis.app.common.keyvalue

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface KeyValueDao {
    @Query("SELECT * FROM keyValue WHERE primaryKey=:key")
    fun get(key: String): KeyValueEntity?

    @Query("SELECT * FROM keyValue WHERE primaryKey=:key")
    fun getStream(key: String): Flowable<KeyValueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(keyValue: KeyValueEntity)
}

@Entity(tableName = "keyValue")
data class KeyValueEntity(@PrimaryKey val primaryKey: String, val value: String)
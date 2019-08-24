package com.satis.app.common.keyvalue

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface KeyValueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keyValue: KeyValueEntity)

    @Query("SELECT * FROM keyValue WHERE primaryKey=:key")
    suspend fun get(key: String): KeyValueEntity?

    @Query("SELECT * FROM keyValue WHERE primaryKey=:key")
    fun getStream(key: String): Flow<KeyValueEntity>
}

@Entity(tableName = "keyValue")
data class KeyValueEntity(@PrimaryKey val primaryKey: String, val value: String)
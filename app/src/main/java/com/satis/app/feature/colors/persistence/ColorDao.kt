package com.satis.app.feature.colors.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface ColorDao {

    @Query("SELECT * FROM colors")
    fun getColors(): Flowable<List<ColorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertColor(color: ColorEntity)
}
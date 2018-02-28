package com.satis.app.feature.colors.persistence

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "colors")
data class ColorEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val color: String
)
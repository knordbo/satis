package com.satis.app.feature.colors.redux

import com.satis.app.feature.colors.persistence.ColorEntity

data class ColorViewState(
        val isLoading: Boolean = false,
        val colors: List<ColorEntity> = emptyList(),
        val successfullyAddedColor: String? = null,
        val successfullyDeletedColor: String? = null,
        val error: Throwable? = null
)